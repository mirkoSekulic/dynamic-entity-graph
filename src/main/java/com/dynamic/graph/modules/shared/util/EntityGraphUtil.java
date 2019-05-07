package com.dynamic.graph.modules.shared.util;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.query.JpaEntityGraph;
import org.springframework.lang.Nullable;

public class EntityGraphUtil {
    private EntityGraphUtil() {
    }

    public static JpaEntityGraph getDynamicGraph(String entityName, EntityGraph.EntityGraphType type, @Nullable String[] attributePaths) {
        return new JpaEntityGraph(
                entityName + (attributePaths != null? "." + String.join(".", attributePaths): ""),
                type,
                attributePaths
        );
    }
}
