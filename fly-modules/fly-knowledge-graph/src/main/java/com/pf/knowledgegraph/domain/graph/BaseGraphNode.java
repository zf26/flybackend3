package com.pf.knowledgegraph.domain.graph;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Property;

/**
 * 图谱节点基类
 * 包含Neo4j节点的基本属性
 *
 * @author fly
 */
@Data
public abstract class BaseGraphNode {

    @Id
    private String id; // Neo4j的elementId
    private String name;
}
