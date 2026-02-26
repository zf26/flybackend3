package com.pf.search.api.factory;

import com.pf.common.core.web.domain.AjaxResult;
import com.pf.search.api.RemoteDoctorSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 医生搜索服务降级处理
 */
@Component
public class RemoteDoctorSearchFallbackFactory implements FallbackFactory<RemoteDoctorSearchService> {

    private static final Logger log = LoggerFactory.getLogger(RemoteDoctorSearchFallbackFactory.class);

    @Override
    public RemoteDoctorSearchService create(Throwable cause) {
        log.error("医生搜索服务调用失败:{}", cause.getMessage());
        return new RemoteDoctorSearchService() {
            @Override
            public AjaxResult quickSearch(String keyword, String department, Integer pageNum, Integer pageSize) {
                return AjaxResult.error("搜索服务暂时不可用，请稍后再试");
            }

            @Override
            public AjaxResult syncDoctorData(Long doctorId) {
                return AjaxResult.error("同步服务暂时不可用，请稍后再试");
            }

            @Override
            public AjaxResult deleteDoctorIndex(Long doctorId) {
                return AjaxResult.error("删除服务暂时不可用，请稍后再试");
            }
        };
    }
}

