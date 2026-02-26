package com.pf.consultation.consultationsmaster.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.pf.common.core.annotation.Excel;
import com.pf.common.core.web.domain.BaseEntity;

/**
 * 问诊主表对象 consultations
 * 
 * @author ruoyi
 * @date 2026-01-14
 */
public class Consultations extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 问诊ID */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 医生ID */
    @Excel(name = "医生ID")
    private Long doctorId;

    /** 用户姓名 */
    @Excel(name = "用户姓名")
    private String userName;

    /** 医生姓名 */
    @Excel(name = "医生姓名")
    private String doctorName;

    /** 就诊类型：初诊/复诊/咨询 */
    @Excel(name = "就诊类型：初诊/复诊/咨询")
    private String consultationType;

    /** 就诊科室 */
    @Excel(name = "就诊科室")
    private String department;

    /** 咨询方式：文字/语音/视频 */
    @Excel(name = "咨询方式：文字/语音/视频")
    private String consultationMethod;

    /** 是否匿名咨询：0-否，1-是 */
    @Excel(name = "是否匿名咨询：0-否，1-是")
    private Integer isAnonymous;

    /** 价格预算：50-100元/100-200元/200-500元/500元以上 */
    @Excel(name = "价格预算：50-100元/100-200元/200-500元/500元以上")
    private String priceBudget;

    /** 紧急程度：普通/紧急/非常紧急 */
    @Excel(name = "紧急程度：普通/紧急/非常紧急")
    private String urgencyLevel;

    /** 问诊描述 */
    @Excel(name = "问诊描述")
    private String description;

    /** 是否同意服务协议：0-否，1-是 */
    @Excel(name = "是否同意服务协议：0-否，1-是")
    private Integer agreeToTerms;

    /** 问诊状态：pending/processing/completed/cancelled */
    @Excel(name = "问诊状态：pending/processing/completed/cancelled")
    private String status;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedAt;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setDoctorId(Long doctorId) 
    {
        this.doctorId = doctorId;
    }

    public Long getDoctorId()
    {
        return doctorId;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setDoctorName(String doctorName)
    {
        this.doctorName = doctorName;
    }

    public String getDoctorName()
    {
        return doctorName;
    }

    public void setConsultationType(String consultationType) 
    {
        this.consultationType = consultationType;
    }

    public String getConsultationType() 
    {
        return consultationType;
    }

    public void setDepartment(String department) 
    {
        this.department = department;
    }

    public String getDepartment() 
    {
        return department;
    }

    public void setConsultationMethod(String consultationMethod) 
    {
        this.consultationMethod = consultationMethod;
    }

    public String getConsultationMethod() 
    {
        return consultationMethod;
    }

    public void setIsAnonymous(Integer isAnonymous) 
    {
        this.isAnonymous = isAnonymous;
    }

    public Integer getIsAnonymous() 
    {
        return isAnonymous;
    }

    public void setPriceBudget(String priceBudget) 
    {
        this.priceBudget = priceBudget;
    }

    public String getPriceBudget() 
    {
        return priceBudget;
    }

    public void setUrgencyLevel(String urgencyLevel) 
    {
        this.urgencyLevel = urgencyLevel;
    }

    public String getUrgencyLevel() 
    {
        return urgencyLevel;
    }

    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }

    public void setAgreeToTerms(Integer agreeToTerms) 
    {
        this.agreeToTerms = agreeToTerms;
    }

    public Integer getAgreeToTerms() 
    {
        return agreeToTerms;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setCreatedAt(Date createdAt) 
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() 
    {
        return createdAt;
    }

    public void setUpdatedAt(Date updatedAt) 
    {
        this.updatedAt = updatedAt;
    }

    public Date getUpdatedAt() 
    {
        return updatedAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("doctorId", getDoctorId())
            .append("userName", getUserName())
            .append("doctorName", getDoctorName())
            .append("consultationType", getConsultationType())
            .append("department", getDepartment())
            .append("consultationMethod", getConsultationMethod())
            .append("isAnonymous", getIsAnonymous())
            .append("priceBudget", getPriceBudget())
            .append("urgencyLevel", getUrgencyLevel())
            .append("description", getDescription())
            .append("agreeToTerms", getAgreeToTerms())
            .append("status", getStatus())
            .append("createdAt", getCreatedAt())
            .append("updatedAt", getUpdatedAt())
            .toString();
    }
}
