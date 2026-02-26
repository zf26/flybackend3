package com.pf.knowledgegraph.domain.graph;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import java.util.Map;

/**
 * 通用节点实体
 * 用于处理Neo4j中各种类型的节点，支持动态属性
 *
 * @author fly
 */
@Data
@Node
public class GenericNode {

    @Id
    private String elementId; // Neo4j的elementId

    @Property("name")
    private String name;

    // 动态属性存储 - 存储Neo4j中的所有属性
    private Map<String, Object> properties;

    // 节点标签（Neo4j中的labels）
    private String[] labels;

    // 便捷方法：获取属性值
    public Object getProperty(String key) {
        return properties != null ? properties.get(key) : null;
    }

    public String getPropertyAsString(String key) {
        Object value = getProperty(key);
        return value != null ? value.toString() : null;
    }

    public Integer getPropertyAsInteger(String key) {
        Object value = getProperty(key);
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    public Double getPropertyAsDouble(String key) {
        Object value = getProperty(key);
        if (value instanceof Double) {
            return (Double) value;
        } else if (value instanceof String) {
            try {
                return Double.parseDouble((String) value);
            } catch (NumberFormatException e) {
                return null;
            }
        } else if (value instanceof Integer) {
            return ((Integer) value).doubleValue();
        }
        return null;
    }

    public Boolean getPropertyAsBoolean(String key) {
        Object value = getProperty(key);
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof String) {
            return Boolean.parseBoolean((String) value);
        }
        return null;
    }
}
