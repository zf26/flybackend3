package com.pf.search.dto;

import lombok.Data;

/**
 * 医生搜索请求
 */
@Data
public class DoctorSearchRequest {

    /**
     * 搜索关键词（医生姓名、擅长领域、疾病等）
     */
    private String keyword;

    /**
     * 科室筛选
     */
    private String department;

    /**
     * 医院筛选
     */
    private String hospital;

    /**
     * 职称筛选
     */
    private String title;

    /**
     * 最低评分
     */
    private Double minRating;

    /**
     * 最高价格
     */
    private Double maxPrice;

    /**
     * 状态筛选（0: 在线, 1: 离线）
     */
    private Integer status;

    /**
     * 排序字段（rating: 评分, price: 价格, consultationCount: 问诊次数）
     */
    private String sortBy = "rating";

    /**
     * 排序方向（asc: 升序, desc: 降序）
     */
    private String sortOrder = "desc";

    /**
     * 页码（从1开始）
     */
    private Integer pageNum = 1;

    /**
     * 每页数量
     */
    private Integer pageSize = 20;
}

