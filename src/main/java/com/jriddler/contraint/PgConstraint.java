package com.jriddler.contraint;

import lombok.AllArgsConstructor;

import java.util.Map;

/**
 * Postgres constraint.
 */
@AllArgsConstructor
public final class PgConstraint implements Constraint {

    /**
     * Constraint name.
     * Retrieved  from pg_constraint.conname
     */
    private final String name;

    /**
     * Constraint value.
     * Retrieved from pg_constraint.pg_get_constraintdef
     */
    private final String value;

    /**
     * Ctor that creates constraint from db row.
     *
     * @param row Db row
     */
    public PgConstraint(final Map<String, Object> row) {
        this.name = row.get("name").toString();
        this.value = row.get("value").toString();
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String value() {
        return this.value;
    }
}
