package com.jriddler.contraint;

/**
 * Db constraint.
 */
public interface Constraint {
    /**
     * Constrains name.
     *
     * @return Name
     */
    String name();

    /**
     * Constrains value.
     *
     * @return Value
     */
    String value();
}
