package com.pf.knowledgegraph.domain.graph;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

/**
 * 疾病-药物关系实体
 *
 * @author fly
 */
@Data
@RelationshipProperties
public class DiseaseDrugRelation {

    @Id
    @GeneratedValue
    private Long id;

    @Property("relationType")
    private String relationType; // "好评药品" 或 "常用药品"

    @Property("effectiveness")
    private Double effectiveness; // 有效性评分

    @Property("usage")
    private String usage; // 用法用量

    @Property("sideEffects")
    private String sideEffects; // 副作用

    @Property("recommendationLevel")
    private String recommendationLevel; // 推荐等级

    @TargetNode
    private DrugNode drug;
}
