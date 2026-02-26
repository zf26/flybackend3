package com.pf.knowledgegraph.repository;

import com.pf.knowledgegraph.domain.graph.SymptomDiseaseRelation;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 症状-疾病关系Repository
 *
 * @author fly
 */
@Repository
public interface SymptomDiseaseRelationRepository extends Neo4jRepository<SymptomDiseaseRelation, Long> {

    /**
     * 查询所有症状-疾病关系
     */
    @Query("MATCH (s:症状)-[r:症状]->(d:疾病) RETURN r")
    List<SymptomDiseaseRelation> findAllRelations();

    /**
     * 根据症状ID查询关系
     */
    @Query("MATCH (s:症状 {elementId: $symptomId})-[r:症状]->(d:疾病) RETURN r")
    List<SymptomDiseaseRelation> findBySymptomId(String symptomId);

    /**
     * 根据疾病ID查询关系
     */
    @Query("MATCH (s:症状)-[r:症状]->(d:疾病 {elementId: $diseaseId}) RETURN r")
    List<SymptomDiseaseRelation> findByDiseaseId(String diseaseId);
}
