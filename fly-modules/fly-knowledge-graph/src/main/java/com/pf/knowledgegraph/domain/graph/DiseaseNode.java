package com.pf.knowledgegraph.domain.graph;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

/**
 * 疾病节点实体
 * 通过继承BaseGraphNode获得name属性，与数据库结构完全匹配
 *
 * 关系处理：
 * - 并发症：getRelationships("并发症")
 * - 所属科室：getRelationships("所属科室")
 * - 症状：getRelationships("症状") // 反向关系
 * - 好评药品：getRelationships("好评药品")
 * - 常用药品：getRelationships("常用药品")
 * - 推荐食谱：getRelationships("推荐食谱")
 * - 宜吃：getRelationships("宜吃")
 * - 忌吃：getRelationships("忌吃")
 * - 诊断检查：getRelationships("诊断检查")
 *
 * @author fly
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Node("疾病")
public class DiseaseNode extends BaseGraphNode {


    private String cureRate;

    private String cause;

    private String treatmentDuration;

    private String susceptiblePopulation;

    private String description;

    private String preventionMethods;
}
