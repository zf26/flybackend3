package com.pf.knowledgegraph.domain.graph;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

/**
 * 疾病-检查手段关系实体
 *
 * @author fly
 */
@Data
@RelationshipProperties
public class DiseaseExaminationRelation {

    @Id
    @GeneratedValue
    private Long id;

    @Property("relationType")
    private String relationType = "诊断检查";

    @Property("necessity")
    private String necessity; // 必要性程度

    @Property("accuracy")
    private Double accuracy; // 准确性

    @Property("cost")
    private String cost; // 费用水平

    @Property("preparation")
    private String preparation; // 检查前准备

    @TargetNode
    private ExaminationMethodNode examination;
}
