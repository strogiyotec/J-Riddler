package com.jriddler.contraint;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public final class PgConstraint implements Constraint {

    private final String name;

    private final String value;

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
