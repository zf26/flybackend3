package com.pf.knowledgegraph.domain.graph;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.neo4j.core.schema.Node;

/**
 * 食物节点实体
 * 根据您的Neo4j业务数据schema，所有节点都只有"名称"属性
 * 通过继承BaseGraphNode获得name属性，与数据库结构完全匹配
 *
 * @author fly
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Node("食物")
public class FoodNode extends BaseGraphNode {


}
