package com.pf.knowledgegraph.repository;

import com.pf.knowledgegraph.domain.graph.*;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 疾病节点Repository
 *
 * @author fly
 */
@Repository
public interface DiseaseRepository extends Neo4jRepository<DiseaseNode, String> {

    /**
     * 根据疾病名称查找疾病
     */
    Optional<DiseaseNode> findByName(String name);


    /**
     * 模糊搜索疾病名称
     */
    @Query("MATCH (d:疾病) WHERE d.name CONTAINS $keyword " +
           "RETURN toString(id(d)) as id, " +
           "coalesce(d.name, '') as name")
    List<DiseaseNode> findByNameContaining(@Param("keyword") String keyword);


    /**
     * 根据疾病ID查找相关的症状
     */
    @Query("MATCH (d:疾病)-[r:症状]-(s:症状) " +
           "WHERE id(d) = toInteger($diseaseId) " +
           "RETURN toString(id(s)) as id, " +
           "coalesce(s.name, '') as name")
    List<SymptomNode> findRelatedSymptoms(@Param("diseaseId") String diseaseId);

    /**
     * 根据疾病ID查找并发症
     */
    @Query("MATCH (d:疾病)-[r:并发症]->(c:疾病) " +
           "WHERE id(d) = toInteger($diseaseId) " +
           "RETURN toString(id(c)) as id, " +
           "coalesce(c.name, '') as name")
    List<DiseaseNode> findComplications(@Param("diseaseId") String diseaseId);

    /**
     * 根据疾病ID查找所属科室
     */
    @Query("MATCH (d:疾病)-[r:所属科室]->(dept:二级科室) " +
           "WHERE id(d) = toInteger($diseaseId) " +
           "RETURN toString(id(dept)) as id, " +
           "coalesce(dept.name, '') as name")
    List<DepartmentNode> findRelatedDepartments(@Param("diseaseId") String diseaseId);

    /**
     * 根据疾病ID查找推荐药物
     */
    @Query("MATCH (d:疾病)-[r:好评药品|常用药品]->(drug:药物) " +
           "WHERE id(d) = toInteger($diseaseId) " +
           "RETURN toString(id(drug)) as id, " +
           "coalesce(drug.name, '') as name")
    List<DrugNode> findRecommendedDrugs(@Param("diseaseId") String diseaseId);

    /**
     * 根据疾病ID查找推荐食谱
     */
    @Query("MATCH (d:疾病)-[r:推荐食谱]->(recipe:食谱) " +
           "WHERE id(d) = toInteger($diseaseId) " +
           "RETURN toString(id(recipe)) as id, " +
           "coalesce(recipe.name, '') as name")
    List<RecipeNode> findRecommendedRecipes(@Param("diseaseId") String diseaseId);

    /**
     * 根据疾病ID查找宜吃食物
     */
    @Query("MATCH (d:疾病)-[r:宜吃]->(food:食物) " +
           "WHERE id(d) = toInteger($diseaseId) " +
           "RETURN toString(id(food)) as id, " +
           "coalesce(food.name, '') as name")
    List<FoodNode> findRecommendedFoods(@Param("diseaseId") String diseaseId);

    /**
     * 根据疾病ID查找忌吃食物
     */
    @Query("MATCH (d:疾病)-[r:忌吃]->(food:食物) " +
           "WHERE id(d) = toInteger($diseaseId) " +
           "RETURN toString(id(food)) as id, " +
           "coalesce(food.name, '') as name")
    List<FoodNode> findAvoidFoods(@Param("diseaseId") String diseaseId);

    /**
     * 根据疾病ID查找诊断检查
     */
    @Query("MATCH (d:疾病)-[r:诊断检查]->(exam:检查手段) " +
           "WHERE id(d) = toInteger($diseaseId) " +
           "RETURN toString(id(exam)) as id, " +
           "coalesce(exam.name, '') as name")
    List<ExaminationMethodNode> findDiagnosticExaminations(@Param("diseaseId") String diseaseId);
}
