package com.pf.knowledgegraph.domain.graph;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

/**
 * 症状-疾病关系实体
 * 根据您的Neo4j业务数据schema，所有关系都只有"名称"属性
 *
 * @author fly
 */
@Data
@RelationshipProperties
public class SymptomDiseaseRelation {

    @Id
    @GeneratedValue
    private Long id;

    @Property("名称")
    private String name; // 根据您的schema，所有关系都只有名称属性

    @TargetNode
    private DiseaseNode disease;
}
