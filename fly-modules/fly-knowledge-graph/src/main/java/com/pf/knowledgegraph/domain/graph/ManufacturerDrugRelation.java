package com.pf.knowledgegraph.domain.graph;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

/**
 * 生产商-药物关系实体
 *
 * @author fly
 */
@Data
@RelationshipProperties
public class ManufacturerDrugRelation {

    @Id
    @GeneratedValue
    private Long id;

    @Property("relationType")
    private String relationType = "生产药品";

    @Property("approvalNumber")
    private String approvalNumber; // 批准文号

    @Property("productionDate")
    private String productionDate; // 生产日期

    @Property("batchNumber")
    private String batchNumber; // 批号

    @Property("qualityStandard")
    private String qualityStandard; // 质量标准

    @TargetNode
    private DrugNode drug;
}
