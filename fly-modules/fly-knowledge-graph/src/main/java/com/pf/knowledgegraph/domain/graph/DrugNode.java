package com.pf.knowledgegraph.domain.graph;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.neo4j.core.schema.Node;

/**
 * 药物节点实体
 * 通过继承BaseGraphNode获得name属性，与数据库结构完全匹配
 *
 * 关系处理：
 * - 好评药品：getRelationships("好评药品") // 与其他药物
 * - 常用药品：getRelationships("常用药品") // 反向关系
 * - 生产药品：getRelationships("生产药品") // 反向关系，与生产商
 *
 * @author fly
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Node("药物")
public class DrugNode extends BaseGraphNode {


}
