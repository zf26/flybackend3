package com.pf.knowledgegraph.repository;

import com.pf.knowledgegraph.domain.graph.RecipeNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 食谱节点Repository
 *
 * @author fly
 */
@Repository
public interface RecipeRepository extends Neo4jRepository<RecipeNode, String> {

    /**
     * 根据食谱名称查找食谱
     */
    Optional<RecipeNode> findByName(String name);


    /**
     * 模糊搜索食谱名称
     */
    @Query("MATCH (r:食谱) WHERE r.名称 CONTAINS $keyword RETURN r")
    List<RecipeNode> findByNameContaining(@Param("keyword") String keyword);
}
