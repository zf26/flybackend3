package com.pf.knowledgegraph.domain.graph;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

/**
 * 疾病-食谱关系实体
 *
 * @author fly
 */
@Data
@RelationshipProperties
public class DiseaseRecipeRelation {

    @Id
    @GeneratedValue
    private Long id;

    @Property("relationType")
    private String relationType = "推荐食谱";

    @Property("suitability")
    private String suitability; // 适用阶段/病情

    @Property("benefits")
    private String benefits; // 食谱益处

    @Property("cautions")
    private String cautions; // 注意事项

    @TargetNode
    private RecipeNode recipe;
}
