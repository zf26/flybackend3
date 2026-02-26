package com.pf.search.dto;

import com.pf.search.document.DoctorDocument;
import lombok.Data;

import java.util.List;

/**
 * 搜索结果响应
 */
@Data
public class SearchResult<T> {

    /**
     * 搜索结果列表
     */
    private List<T> items;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 当前页码
     */
    private Integer pageNum;

    /**
     * 每页数量
     */
    private Integer pageSize;

    /**
     * 总页数
     */
    private Integer totalPages;

    /**
     * 搜索耗时（毫秒）
     */
    private Long took;

    public SearchResult() {
    }

    public SearchResult(List<T> items, Long total, Integer pageNum, Integer pageSize) {
        this.items = items;
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalPages = (int) Math.ceil((double) total / pageSize);
    }
}

