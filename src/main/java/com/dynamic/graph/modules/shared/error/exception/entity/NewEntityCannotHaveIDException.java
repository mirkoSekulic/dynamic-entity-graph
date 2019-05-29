package com.dynamic.graph.modules.shared.error.exception.entity;

public class NewEntityCannotHaveIDException extends RuntimeException
{
    private static final long serialVersionUID = 1528515452773248674L;

    private final String entityName;

    public NewEntityCannotHaveIDException(String entityName)
    {
        this.entityName = entityName;
    }

    @Override
    public String getMessage()
    {
        return String.format("A new entity '%s' cannot have a ID.", entityName);
    }
}
