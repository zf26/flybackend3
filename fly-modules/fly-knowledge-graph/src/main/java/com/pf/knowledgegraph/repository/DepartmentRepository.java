package com.pf.knowledgegraph.repository;

import com.pf.knowledgegraph.domain.graph.DepartmentNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 科室节点Repository
 *
 * @author fly
 */
@Repository
public interface DepartmentRepository extends Neo4jRepository<DepartmentNode, String> {

    /**
     * 根据科室名称查找科室
     */
    Optional<DepartmentNode> findByName(String name);


    /**
     * 查找一级科室
     */
    @Query("MATCH (d:一级科室) RETURN d")
    List<DepartmentNode> findPrimaryDepartments();

    /**
     * 查找二级科室
     */
    @Query("MATCH (d:二级科室) RETURN d")
    List<DepartmentNode> findSecondaryDepartments();

    /**
     * 根据二级科室ID查找上级一级科室
     */
    @Query("MATCH (secondary:二级科室 {departmentId: $departmentId})-[r:属于]->(primary:一级科室) RETURN primary")
    Optional<DepartmentNode> findParentDepartment(@Param("departmentId") String departmentId);

    /**
     * 根据一级科室ID查找下级二级科室
     */
    @Query("MATCH (primary:一级科室 {departmentId: $departmentId})<-[r:属于]-(secondary:二级科室) RETURN secondary")
    List<DepartmentNode> findChildDepartments(@Param("departmentId") String departmentId);
}
