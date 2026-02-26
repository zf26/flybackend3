package com.pf.search.api;

import com.pf.common.core.constant.ServiceNameConstants;
import com.pf.common.core.web.domain.AjaxResult;
import com.pf.search.api.domain.DoctorSearchDTO;
import com.pf.search.api.factory.RemoteDoctorSearchFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 医生搜索服务 Feign 接口
 */
@FeignClient(
    contextId = "remoteDoctorSearchService",
    value = ServiceNameConstants.SEARCH_SERVICE,
    fallbackFactory = RemoteDoctorSearchFallbackFactory.class
)
public interface RemoteDoctorSearchService {

    /**
     * 快速搜索医生
     *
     * @param keyword 搜索关键词
     * @param department 科室
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 搜索结果
     */
    @GetMapping("/search/doctor")
    AjaxResult quickSearch(
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) String department,
        @RequestParam(defaultValue = "1") Integer pageNum,
        @RequestParam(defaultValue = "20") Integer pageSize
    );

    /**
     * 同步医生数据到 ES
     *
     * @param doctorId 医生ID，为空则同步所有
     * @return 操作结果
     */
    @PostMapping("/search/doctor/sync")
    AjaxResult syncDoctorData(@RequestParam(required = false) Long doctorId);

    /**
     * 删除医生索引
     *
     * @param doctorId 医生ID
     * @return 操作结果
     */
    @DeleteMapping("/search/doctor/{doctorId}")
    AjaxResult deleteDoctorIndex(@PathVariable("doctorId") Long doctorId);
}

