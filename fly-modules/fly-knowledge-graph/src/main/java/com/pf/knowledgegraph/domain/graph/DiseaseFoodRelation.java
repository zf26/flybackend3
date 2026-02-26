package com.pf.knowledgegraph.domain.graph;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

/**
 * 疾病-食物关系实体
 *
 * @author fly
 */
@Data
@RelationshipProperties
public class DiseaseFoodRelation {

    @Id
    @GeneratedValue
    private Long id;

    @Property("relationType")
    private String relationType; // "宜吃" 或 "忌吃"

    @Property("reason")
    private String reason; // 推荐/忌讳原因

    @Property("amount")
    private String amount; // 建议摄入量

    @Property("frequency")
    private String frequency; // 食用频率

    @TargetNode
    private FoodNode food;
}
