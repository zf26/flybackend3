package com.pf.knowledgegraph.domain.graph;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

/**
 * 疾病并发症关系实体
 *
 * @author fly
 */
@Data
@RelationshipProperties
public class DiseaseComplicationRelation {

    @Id
    @GeneratedValue
    private Long id;

    @Property("relationType")
    private String relationType = "并发症";

    @Property("probability")
    private Double probability; // 并发概率

    @Property("severity")
    private String severity; // 严重程度

    @Property("description")
    private String description;

    @TargetNode
    private DiseaseNode complicationDisease; // 并发症疾病
}
