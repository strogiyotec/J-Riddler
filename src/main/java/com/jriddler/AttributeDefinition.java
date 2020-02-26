package com.jriddler;

public interface AttributeDefinition {

    Object value();

    int size();

    String name();

    DefinitionType type();

    static enum DefinitionType {
        STRING,
        NUMBER
    }
}
