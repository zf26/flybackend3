package com.pf.search.repository;

import com.pf.search.document.DoctorDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 医生搜索 Repository
 */
@Repository
public interface DoctorSearchRepository extends ElasticsearchRepository<DoctorDocument, Long> {

    /**
     * 按科室查询
     */
    List<DoctorDocument> findByDepartment(String department);

    /**
     * 按医院查询
     */
    List<DoctorDocument> findByHospital(String hospital);

    /**
     * 按姓名模糊查询
     */
    List<DoctorDocument> findByNameContaining(String name);
}

