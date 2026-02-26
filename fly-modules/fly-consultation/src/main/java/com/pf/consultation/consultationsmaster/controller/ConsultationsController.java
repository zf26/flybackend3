package com.pf.consultation.consultationsmaster.controller;

import java.util.List;
import java.io.IOException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pf.common.log.annotation.Log;
import com.pf.common.log.enums.BusinessType;
import com.pf.common.security.annotation.RequiresPermissions;
import com.pf.consultation.consultationsmaster.domain.Consultations;
import com.pf.consultation.consultationsmaster.service.IConsultationsService;
import com.pf.common.core.web.controller.BaseController;
import com.pf.common.core.web.domain.AjaxResult;
import com.pf.common.core.utils.poi.ExcelUtil;
import com.pf.common.core.web.page.TableDataInfo;

/**
 * 问诊主表Controller
 * 
 * @author ruoyi
 * @date 2026-01-14
 */
@RestController
@RequestMapping("/consultationsmaster")
public class ConsultationsController extends BaseController
{
    @Autowired
    private IConsultationsService consultationsService;

    /**
     * 查询问诊主表列表
     */
    @RequiresPermissions("consultation:consultationsmaster:list")
    @GetMapping("/list")
    public TableDataInfo list(Consultations consultations)
    {
        startPage();
        List<Consultations> list = consultationsService.selectConsultationsList(consultations);
        return getDataTable(list);
    }

    /**
     * 导出问诊主表列表
     */
    @RequiresPermissions("consultation:consultationsmaster:export")
    @Log(title = "问诊主表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Consultations consultations)
    {
        List<Consultations> list = consultationsService.selectConsultationsList(consultations);
        ExcelUtil<Consultations> util = new ExcelUtil<Consultations>(Consultations.class);
        util.exportExcel(response, list, "问诊主表数据");
    }

    /**
     * 获取问诊主表详细信息
     */
    @RequiresPermissions("consultation:consultationsmaster:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(consultationsService.selectConsultationsById(id));
    }

    /**
     * 新增问诊主表
     */
    @RequiresPermissions("consultation:consultationsmaster:add")
    @Log(title = "问诊主表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Consultations consultations)
    {
        return toAjax(consultationsService.insertConsultations(consultations));
    }

    /**
     * 修改问诊主表
     */
    @RequiresPermissions("consultation:consultationsmaster:edit")
    @Log(title = "问诊主表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Consultations consultations)
    {
        return toAjax(consultationsService.updateConsultations(consultations));
    }

    /**
     * 删除问诊主表
     */
    @RequiresPermissions("consultation:consultationsmaster:remove")
    @Log(title = "问诊主表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(consultationsService.deleteConsultationsByIds(ids));
    }
}
