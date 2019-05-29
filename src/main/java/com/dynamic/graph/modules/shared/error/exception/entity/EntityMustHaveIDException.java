package com.dynamic.graph.modules.shared.error.exception.entity;

public class EntityMustHaveIDException extends RuntimeException
{
    private static final long serialVersionUID = 1761903020773388435L;

    private final String entityName;

    public EntityMustHaveIDException(String entityName)
    {
        this.entityName = entityName;
    }

    @Override
    public String getMessage()
    {
        return String.format("An entity '%s' must have a ID.", entityName);
    }
}
