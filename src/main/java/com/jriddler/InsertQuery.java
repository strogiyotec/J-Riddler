package com.jriddler;

import com.jriddler.attrs.AttributeDefinition;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public final class InsertQuery {

    private final List<AttributeDefinition> attributes;

    private final String tableName;

    public String build() {
        final StringBuilder queryBuilder =
                new StringBuilder(
                        this.tableName.length() * this.attributes.size()
                );
        this.insertInto(queryBuilder);
        this.values(queryBuilder);
        return queryBuilder.toString();
    }

    private void values(final StringBuilder queryBuilder) {
        queryBuilder.append("VALUES \n(");
        for (int i = 0; i < this.attributes.size(); i++) {
            queryBuilder.append("?");
            if (i != this.attributes.size() - 1) {
                queryBuilder.append(",");
            }
        }
        queryBuilder.append(")\n");
    }

    private void insertInto(final StringBuilder queryBuilder) {
        queryBuilder.append("INSERT INTO ").append(this.tableName).append("\n(");
        for (int i = 0; i < this.attributes.size(); i++) {
            final AttributeDefinition attribute = this.attributes.get(i);
            queryBuilder.append(attribute.name());
            if (i != this.attributes.size() - 1) {
                queryBuilder.append(",");
            }
        }
        queryBuilder.append(")\n");
    }
}
