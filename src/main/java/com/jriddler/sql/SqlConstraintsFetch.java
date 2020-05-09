package com.jriddler.sql;

import com.jriddler.contraint.Constraint;
import com.jriddler.contraint.PgConstraint;
import lombok.AllArgsConstructor;
import org.codejargon.fluentjdbc.api.mapper.Mappers;
import org.codejargon.fluentjdbc.api.query.Query;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Get all constraints for specific table.
 */
@AllArgsConstructor
@SuppressWarnings("LineLength")
public final class SqlConstraintsFetch {

    /**
     * Table name.
     */
    private final String tableName;

    /**
     * Query.
     */
    private final Query query;

    /**
     * Retrieves all constraints as Map.
     * Key in map is a column name
     * Value is a list of constraints for this column
     *
     * @return Constraints per column
     */
    public Map<String, List<PgConstraint>> perform() {
        final List<Map<String, Object>> constraints = this.query.select(this.query()).listResult(Mappers.map());
        return constraints
                .stream()
                .collect(
                        Collectors.groupingBy(
                                map -> map.get("attribute").toString(),
                                Collectors.mapping(
                                        PgConstraint::new,
                                        Collectors.toList()
                                )
                        )
                );
    }

    /**
     * Creates query that fetches constraints.
     *
     * @return Select query
     */
    private String query() {
        return String.format(
                String.join(
                        "\n",
                        "SELECT c.conname as name,",
                        "pg_get_constraintdef(c.oid) as value,",
                        "a.attname as attribute",
                        "FROM pg_constraint c",
                        "INNER JOIN pg_namespace n",
                        "ON n.oid = c.connamespace",
                        "CROSS JOIN LATERAL unnest(c.conkey) ak(k)",
                        "INNER JOIN pg_attribute a",
                        "ON a.attrelid = c.conrelid",
                        "AND a.attnum = ak.k",
                        "WHERE c.conrelid::regclass::text = '%s'",
                        "ORDER BY c.contype;"
                ), this.tableName
        );
    }
}
