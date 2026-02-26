package com.pf.knowledgegraph.domain.graph;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.neo4j.core.schema.Node;

/**
 * 检查手段节点实体
 * 通过继承BaseGraphNode获得name属性，与数据库结构完全匹配
 *
 * @author fly
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Node("检查手段")
public class ExaminationMethodNode extends BaseGraphNode {


}
