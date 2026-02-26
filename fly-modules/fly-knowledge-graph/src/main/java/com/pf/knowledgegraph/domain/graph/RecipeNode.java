package com.pf.knowledgegraph.domain.graph;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.neo4j.core.schema.Node;

/**
 * 食谱节点实体
 * 通过继承BaseGraphNode获得name属性，与数据库结构完全匹配
 *
 * @author fly
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Node("食谱")
public class RecipeNode extends BaseGraphNode {


}
