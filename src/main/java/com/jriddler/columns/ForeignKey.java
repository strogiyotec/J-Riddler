package com.jriddler.columns;

public interface ForeignKey {

    String fkTableName();

    String fkColumnName();

    String pkTableName();

    String pkColumnName();

}
