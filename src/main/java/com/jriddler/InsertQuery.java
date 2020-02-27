package com.jriddler;

import com.jriddler.attrs.AttributeDefinition;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Create insert query for attributes.
 */
@AllArgsConstructor
public final class InsertQuery {

    /**
     * List of attrs.
     */
    private final List<AttributeDefinition> attributes;

    /**
     * Table name.
     */
    private final String tableName;

    /**
     * Build insert query.
     *
     * @return Insert query
     */
    public String build() {
        final StringBuilder queryBuilder =
                new StringBuilder(
                        this.tableName.length() * this.attributes.size()
                );
        this.insertInto(queryBuilder);
        this.values(queryBuilder);
        return queryBuilder.toString();
    }

    /**
     * Create values part of insert query.
     *
     * @param queryBuilder QueryBuilder
     */
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

    /**
     * Create insert part of insert query.
     *
     * @param queryBuilder QueryBuilder
     */
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
