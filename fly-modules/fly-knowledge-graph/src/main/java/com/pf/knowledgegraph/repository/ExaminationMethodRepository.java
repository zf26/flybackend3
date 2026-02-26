package com.pf.knowledgegraph.repository;

import com.pf.knowledgegraph.domain.graph.ExaminationMethodNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 检查手段节点Repository
 *
 * @author fly
 */
@Repository
public interface ExaminationMethodRepository extends Neo4jRepository<ExaminationMethodNode, String> {

    /**
     * 根据检查名称查找检查
     */
    Optional<ExaminationMethodNode> findByName(String name);


    /**
     * 模糊搜索检查名称
     */
    @Query("MATCH (e:检查手段) WHERE e.名称 CONTAINS $keyword RETURN e")
    List<ExaminationMethodNode> findByNameContaining(@Param("keyword") String keyword);

    /**
     * 根据检查ID查找相关疾病
     */
    @Query("MATCH (e:检查手段 {examId: $examId})<-[r:诊断检查]-(d:疾病) RETURN d")
    List<com.pf.knowledgegraph.domain.graph.DiseaseNode> findRelatedDiseases(@Param("examId") String examId);

    /**
     * 查找相关的检查手段
     */
    @Query("MATCH (e:检查手段 {examId: $examId})-[r:诊断检查]-(e2:检查手段) " +
           "WHERE e2.examId <> $examId RETURN e2")
    List<ExaminationMethodNode> findRelatedExaminations(@Param("examId") String examId);
}
