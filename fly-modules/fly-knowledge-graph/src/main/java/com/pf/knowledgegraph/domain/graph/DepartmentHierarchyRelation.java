package com.pf.knowledgegraph.domain.graph;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

/**
 * 科室层级关系实体
 *
 * @author fly
 */
@Data
@RelationshipProperties
public class DepartmentHierarchyRelation {

    @Id
    @GeneratedValue
    private Long id;

    @Property("relationType")
    private String relationType = "属于";

    @Property("hierarchyLevel")
    private String hierarchyLevel; // 层级关系描述

    @TargetNode
    private DepartmentNode parentDepartment;
}
