package com.pf.knowledgegraph.repository;

import com.pf.knowledgegraph.domain.graph.DiseaseNode;
import com.pf.knowledgegraph.domain.graph.SymptomNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 症状节点Repository
 *
 * @author fly
 */
@Repository
public interface SymptomRepository extends Neo4jRepository<SymptomNode, String> {

    /**
     * 根据症状名称查找症状
     */
    Optional<SymptomNode> findByName(String name);


    /**
     * 模糊搜索症状名称
     */
    @Query("MATCH (s:症状) WHERE s.name CONTAINS $keyword " +
           "RETURN toString(id(s)) as id, " +
           "coalesce(s.name, '') as name")
    List<SymptomNode> findByNameContaining(@Param("keyword") String keyword);


    /**
     * 根据症状ID查找相关的疾病
     */
    @Query("MATCH (s:症状)-[r:症状]->(d:疾病) " +
           "WHERE id(s) = $symptomId " +
           "RETURN toString(id(d)) as id, " +
           "coalesce(d.name, '') as name, " +
           "coalesce(d.cureRate, '') as cureRate, " +
           "coalesce(d.cause, '') as cause, " +
           "coalesce(d.treatmentDuration, '') as treatmentDuration, " +
           "coalesce(d.susceptiblePopulation, '') as susceptiblePopulation, " +
           "coalesce(d.description, '') as description, " +
           "coalesce(d.preventionMethods, '') as preventionMethods")
    List<DiseaseNode> findRelatedDiseases(@Param("symptomId") Long symptomId);

    /**
     * 根据疾病ID查找相关症状
     */
    @Query("MATCH (d:疾病 {diseaseId: $diseaseId})<-[r:症状]-(s:症状) " +
           "RETURN toString(id(s)) as id, " +
           "coalesce(s.name, '') as name")
    List<SymptomNode> findSymptomsByDisease(@Param("diseaseId") String diseaseId);
}
