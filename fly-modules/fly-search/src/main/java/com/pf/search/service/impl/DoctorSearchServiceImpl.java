package com.pf.search.service.impl;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.pf.search.document.DoctorDocument;
import com.pf.search.dto.DoctorSearchRequest;
import com.pf.search.dto.SearchResult;
import com.pf.search.repository.DoctorSearchRepository;
import com.pf.search.service.DoctorSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 医生搜索服务实现
 */
@Slf4j
@Service
public class DoctorSearchServiceImpl implements DoctorSearchService {

    private final DoctorSearchRepository doctorSearchRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    public DoctorSearchServiceImpl(DoctorSearchRepository doctorSearchRepository,
                                   ElasticsearchOperations elasticsearchOperations) {
        this.doctorSearchRepository = doctorSearchRepository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public SearchResult<DoctorDocument> searchDoctors(DoctorSearchRequest request) {
        long startTime = System.currentTimeMillis();

        // 构建查询条件
        List<Query> mustQueries = new ArrayList<>();

        // 关键词搜索（多字段匹配）
        if (request.getKeyword() != null && !request.getKeyword().isEmpty()) {
            Query multiMatchQuery = Query.of(q -> q
                .multiMatch(m -> m
                    .query(request.getKeyword())
                    .fields("name^3", "specialties^2", "introduction", "hospital")
                )
            );
            mustQueries.add(multiMatchQuery);
        }

        // 科室筛选
        if (request.getDepartment() != null && !request.getDepartment().isEmpty()) {
            Query departmentQuery = Query.of(q -> q
                .term(t -> t
                    .field("department")
                    .value(request.getDepartment())
                )
            );
            mustQueries.add(departmentQuery);
        }

        // 医院筛选
        if (request.getHospital() != null && !request.getHospital().isEmpty()) {
            Query hospitalQuery = Query.of(q -> q
                .match(m -> m
                    .field("hospital")
                    .query(request.getHospital())
                )
            );
            mustQueries.add(hospitalQuery);
        }

        // 职称筛选
        if (request.getTitle() != null && !request.getTitle().isEmpty()) {
            Query titleQuery = Query.of(q -> q
                .term(t -> t
                    .field("title")
                    .value(request.getTitle())
                )
            );
            mustQueries.add(titleQuery);
        }

        // 评分筛选
        if (request.getMinRating() != null) {
            Query ratingQuery = Query.of(q -> q
                .range(r -> r
                    .field("rating")
                    .gte(co.elastic.clients.json.JsonData.of(request.getMinRating()))
                )
            );
            mustQueries.add(ratingQuery);
        }

        // 价格筛选
        if (request.getMaxPrice() != null) {
            Query priceQuery = Query.of(q -> q
                .range(r -> r
                    .field("price")
                    .lte(co.elastic.clients.json.JsonData.of(request.getMaxPrice()))
                )
            );
            mustQueries.add(priceQuery);
        }

        // 状态筛选
        if (request.getStatus() != null) {
            Query statusQuery = Query.of(q -> q
                .term(t -> t
                    .field("status")
                    .value(request.getStatus())
                )
            );
            mustQueries.add(statusQuery);
        }

        // 构建 BoolQuery
        BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();
        if (!mustQueries.isEmpty()) {
            boolQueryBuilder.must(mustQueries);
        }

        // 排序
        Sort.Direction direction = "asc".equalsIgnoreCase(request.getSortOrder())
            ? Sort.Direction.ASC
            : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, request.getSortBy());

        // 分页
        PageRequest pageRequest = PageRequest.of(
            request.getPageNum() - 1,
            request.getPageSize(),
            sort
        );

        // 构建查询
        NativeQuery searchQuery = NativeQuery.builder()
            .withQuery(Query.of(q -> q.bool(boolQueryBuilder.build())))
            .withPageable(pageRequest)
            .build();

        // 执行搜索
        SearchHits<DoctorDocument> searchHits = elasticsearchOperations.search(
            searchQuery,
            DoctorDocument.class
        );

        // 提取结果
        List<DoctorDocument> doctors = searchHits.getSearchHits().stream()
            .map(SearchHit::getContent)
            .collect(Collectors.toList());

        // 构建返回结果
        SearchResult<DoctorDocument> result = new SearchResult<>(
            doctors,
            searchHits.getTotalHits(),
            request.getPageNum(),
            request.getPageSize()
        );
        result.setTook(System.currentTimeMillis() - startTime);

        log.info("搜索医生完成，关键词: {}, 结果数: {}, 耗时: {}ms",
            request.getKeyword(), result.getTotal(), result.getTook());

        return result;
    }

    @Override
    public void syncDoctorData(Long doctorId) {
        // TODO: 从数据库查询医生数据并同步到 ES
        // 1. sys_user_doctor sys_user联表查询
        // 2. 转换为 DoctorDocument
        // 3. 保存到 ES
        log.info("同步医生数据到 ES: {}", doctorId);
    }

    @Override
    public void deleteDoctorIndex(Long doctorId) {
        doctorSearchRepository.deleteById(doctorId);
        log.info("删除医生索引: {}", doctorId);
    }
}

