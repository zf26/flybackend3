package com.pf.knowledgegraph.domain.graph;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

/**
 * 疾病-科室关系实体
 *
 * @author fly
 */
@Data
@RelationshipProperties
public class DiseaseDepartmentRelation {

    @Id
    @GeneratedValue
    private Long id;

    @Property("relationType")
    private String relationType = "所属科室";

    @Property("isPrimary")
    private Boolean isPrimary; // 是否为主治科室

    @Property("expertise")
    private String expertise; // 专科优势

    @TargetNode
    private DepartmentNode department;
}
