package com.pf.consultation.consultationsmaster.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pf.consultation.consultationsmaster.mapper.ConsultationsMapper;
import com.pf.consultation.consultationsmaster.domain.Consultations;
import com.pf.consultation.consultationsmaster.service.IConsultationsService;

/**
 * 问诊主表Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-01-14
 */
@Service
public class ConsultationsServiceImpl implements IConsultationsService 
{
    @Autowired
    private ConsultationsMapper consultationsMapper;

    /**
     * 查询问诊主表
     * 
     * @param id 问诊主表主键
     * @return 问诊主表
     */
    @Override
    public Consultations selectConsultationsById(Long id)
    {
        return consultationsMapper.selectConsultationsById(id);
    }

    /**
     * 查询问诊主表列表
     * 
     * @param consultations 问诊主表
     * @return 问诊主表
     */
    @Override
    public List<Consultations> selectConsultationsList(Consultations consultations)
    {
        return consultationsMapper.selectConsultationsList(consultations);
    }

    /**
     * 新增问诊主表
     * 
     * @param consultations 问诊主表
     * @return 结果
     */
    @Override
    public int insertConsultations(Consultations consultations)
    {
        return consultationsMapper.insertConsultations(consultations);
    }

    /**
     * 修改问诊主表
     * 
     * @param consultations 问诊主表
     * @return 结果
     */
    @Override
    public int updateConsultations(Consultations consultations)
    {
        return consultationsMapper.updateConsultations(consultations);
    }

    /**
     * 批量删除问诊主表
     * 
     * @param ids 需要删除的问诊主表主键
     * @return 结果
     */
    @Override
    public int deleteConsultationsByIds(Long[] ids)
    {
        return consultationsMapper.deleteConsultationsByIds(ids);
    }

    /**
     * 删除问诊主表信息
     * 
     * @param id 问诊主表主键
     * @return 结果
     */
    @Override
    public int deleteConsultationsById(Long id)
    {
        return consultationsMapper.deleteConsultationsById(id);
    }
}
