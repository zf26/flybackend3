package com.pf.search.controller;

import com.pf.common.core.web.controller.BaseController;
import com.pf.common.core.web.domain.AjaxResult;
import com.pf.search.document.DoctorDocument;
import com.pf.search.dto.DoctorSearchRequest;
import com.pf.search.dto.SearchResult;
import com.pf.search.service.DoctorSearchService;
import org.springframework.web.bind.annotation.*;

/**
 * 医生搜索控制器
 */
@RestController
@RequestMapping("/search/doctor")
public class DoctorSearchController extends BaseController {

    private final DoctorSearchService doctorSearchService;

    public DoctorSearchController(DoctorSearchService doctorSearchService) {
        this.doctorSearchService = doctorSearchService;
    }

    /**
     * 搜索医生
     *
     * @param request 搜索请求
     * @return 搜索结果
     */
    @PostMapping
    public AjaxResult searchDoctors(@RequestBody DoctorSearchRequest request) {
        SearchResult<DoctorDocument> result = doctorSearchService.searchDoctors(request);
        return success(result);
    }

    /**
     * 快速搜索（GET 方式，支持简单关键词搜索）
     *
     * @param keyword 搜索关键词
     * @param department 科室
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 搜索结果
     */
    @GetMapping
    public AjaxResult quickSearch(
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) String department,
        @RequestParam(defaultValue = "1") Integer pageNum,
        @RequestParam(defaultValue = "20") Integer pageSize
    ) {
        DoctorSearchRequest request = new DoctorSearchRequest();
        request.setKeyword(keyword);
        request.setDepartment(department);
        request.setPageNum(pageNum);
        request.setPageSize(pageSize);

        SearchResult<DoctorDocument> result = doctorSearchService.searchDoctors(request);
        return success(result);
    }

    /**
     * 同步医生数据到 ES
     *
     * @param doctorId 医生ID，为空则同步所有
     * @return 操作结果
     */
    @PostMapping("/sync")
    public AjaxResult syncDoctorData(@RequestParam(required = false) Long doctorId) {
        doctorSearchService.syncDoctorData(doctorId);
        return success("同步成功");
    }

    /**
     * 删除医生索引
     *
     * @param doctorId 医生ID
     * @return 操作结果
     */
    @DeleteMapping("/{doctorId}")
    public AjaxResult deleteDoctorIndex(@PathVariable Long doctorId) {
        doctorSearchService.deleteDoctorIndex(doctorId);
        return success("删除成功");
    }
}

