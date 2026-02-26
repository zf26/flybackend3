package com.pf.knowledgegraph.domain.graph;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.neo4j.core.schema.*;

import java.util.Map;
import java.util.Set;

/**
 * 症状节点实体
 * 通过继承BaseGraphNode获得name属性，与数据库结构完全匹配
 *
 * 关系处理方式：
 * 1. 可以使用基类的动态关系处理（推荐）：getRelationships("症状")
 * 2. 也可以定义特定的关系属性（可选）：@Relationship注解
 *
 * @author fly
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Node("症状")
public class SymptomNode extends BaseGraphNode {

}
