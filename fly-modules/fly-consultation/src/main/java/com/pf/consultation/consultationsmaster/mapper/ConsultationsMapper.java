package com.pf.consultation.consultationsmaster.mapper;

import java.util.List;
import com.pf.consultation.consultationsmaster.domain.Consultations;

/**
 * 问诊主表Mapper接口
 * 
 * @author ruoyi
 * @date 2026-01-14
 */
public interface ConsultationsMapper 
{
    /**
     * 查询问诊主表
     * 
     * @param id 问诊主表主键
     * @return 问诊主表
     */
    public Consultations selectConsultationsById(Long id);

    /**
     * 查询问诊主表列表
     * 
     * @param consultations 问诊主表
     * @return 问诊主表集合
     */
    public List<Consultations> selectConsultationsList(Consultations consultations);

    /**
     * 新增问诊主表
     * 
     * @param consultations 问诊主表
     * @return 结果
     */
    public int insertConsultations(Consultations consultations);

    /**
     * 修改问诊主表
     * 
     * @param consultations 问诊主表
     * @return 结果
     */
    public int updateConsultations(Consultations consultations);

    /**
     * 删除问诊主表
     * 
     * @param id 问诊主表主键
     * @return 结果
     */
    public int deleteConsultationsById(Long id);

    /**
     * 批量删除问诊主表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteConsultationsByIds(Long[] ids);
}
