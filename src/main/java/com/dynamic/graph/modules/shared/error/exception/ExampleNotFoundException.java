package com.dynamic.graph.modules.shared.error.exception;


import com.dynamic.graph.modules.shared.error.exception.entity.EntityDoesNotExistException;

public class ExampleNotFoundException extends EntityDoesNotExistException
{
    private static final long serialVersionUID = -363437578101642142L;

    public ExampleNotFoundException(Object entityIdentificator)
    {
        super("Example", entityIdentificator);
    }
}
