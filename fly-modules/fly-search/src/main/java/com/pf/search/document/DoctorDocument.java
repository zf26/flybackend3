package com.pf.search.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 医生搜索文档
 */
@Data
@Document(indexName = "doctors")
public class DoctorDocument {

    @Id
    private Long id;

    /**
     * 医生姓名
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String name;

    /**
     * 职称（主任医师、副主任医师、主治医师等）
     */
    @Field(type = FieldType.Keyword)
    private String title;

    /**
     * 科室
     */
    @Field(type = FieldType.Keyword)
    private String department;

    /**
     * 医院名称
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String hospital;

    /**
     * 擅长领域
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String specialties;

    /**
     * 简介
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String introduction;

    /**
     * 头像
     */
    @Field(type = FieldType.Keyword)
    private String avatar;

    /**
     * 评分
     */
    @Field(type = FieldType.Double)
    private Double rating;

    /**
     * 问诊次数
     */
    @Field(type = FieldType.Integer)
    private Integer consultationCount;

    /**
     * 价格（元）
     */
    @Field(type = FieldType.Double)
    private Double price;

    /**
     * 状态（0: 在线, 1: 离线）
     */
    @Field(type = FieldType.Integer)
    private Integer status;

    /**
     * 标签（多个标签用逗号分隔）
     */
    @Field(type = FieldType.Keyword)
    private String tags;
}

