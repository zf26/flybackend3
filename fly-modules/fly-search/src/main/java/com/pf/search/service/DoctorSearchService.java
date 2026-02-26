package com.pf.search.service;

import com.pf.search.document.DoctorDocument;
import com.pf.search.dto.DoctorSearchRequest;
import com.pf.search.dto.SearchResult;

/**
 * 医生搜索服务
 */
public interface DoctorSearchService {

    /**
     * 搜索医生
     *
     * @param request 搜索请求
     * @return 搜索结果
     */
    SearchResult<DoctorDocument> searchDoctors(DoctorSearchRequest request);

    /**
     * 同步医生数据到 Elasticsearch
     *
     * @param doctorId 医生ID，为空则同步所有
     */
    void syncDoctorData(Long doctorId);

    /**
     * 删除医生索引
     *
     * @param doctorId 医生ID
     */
    void deleteDoctorIndex(Long doctorId);
}

