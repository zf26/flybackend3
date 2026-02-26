package com.pf.knowledgegraph.repository;

import com.pf.knowledgegraph.domain.graph.DrugNode;
import com.pf.knowledgegraph.domain.graph.ManufacturerNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 药物节点Repository
 *
 * @author fly
 */
@Repository
public interface DrugRepository extends Neo4jRepository<DrugNode, String> {

    /**
     * 根据药物名称查找药物
     */
    Optional<DrugNode> findByName(String name);


    /**
     * 模糊搜索药物名称
     */
    @Query("MATCH (d:药物) WHERE d.name CONTAINS $keyword " +
           "RETURN toString(id(d)) as id, " +
           "coalesce(d.name, '') as name")
    List<DrugNode> findByNameContaining(@Param("keyword") String keyword);

    /**
     * 根据药物ID查找生产商
     */
    @Query("MATCH (m:生产商)-[r:生产药品]->(d:药物 {drugId: $drugId}) " +
           "RETURN toString(id(m)) as id, " +
           "coalesce(m.name, '') as name")
    List<ManufacturerNode> findManufacturersByDrug(@Param("drugId") String drugId);

    /**
     * 根据生产商ID查找生产的药物
     */
    @Query("MATCH (m:生产商 {manufacturerId: $manufacturerId})-[r:生产药品]->(d:药物) " +
           "RETURN toString(id(d)) as id, " +
           "coalesce(d.name, '') as name")
    List<DrugNode> findDrugsByManufacturer(@Param("manufacturerId") String manufacturerId);

    /**
     * 查找替代药物（好评药品关系）
     */
    @Query("MATCH (d:药物 {drugId: $drugId})-[r:好评药品]-(d2:药物) " +
           "WHERE d2.drugId <> $drugId " +
           "RETURN toString(id(d2)) as id, " +
           "coalesce(d2.name, '') as name")
    List<DrugNode> findAlternativeDrugs(@Param("drugId") String drugId);
}
