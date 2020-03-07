package com.jriddler.sql;

import com.jriddler.contraint.Constraint;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Test {@link SqlConstraintFetch}.
 */
@SuppressWarnings("MagicNumber")
public final class SqlConstraintFetchTestCase extends TestDatabase {

    /**
     * Sql constraint to test.
     */
    private SqlConstraintFetch sqlConstraint;

    /**
     * Init.
     */
    @Before
    public void init() {
        this.sqlConstraint = new SqlConstraintFetch(
                "users",
                this.jdbcTemplate
        );
    }

    /**
     * Test that constraints were matched by column name.
     */
    @Test
    public void testFetchConstraints() {
        final Map<String, List<Constraint>> constraints =
                this.sqlConstraint.perform();
        Assert.assertThat(
                constraints.get("id").get(0).name(),
                CoreMatchers.is("users_pkey")
        );
        Assert.assertThat(
                constraints.get("birthday").get(0).name(),
                CoreMatchers.is("birthday_value")
        );
        Assert.assertThat(
                constraints.get("name").get(0).name(),
                CoreMatchers.is("name_length")
        );
        Assert.assertThat(
                constraints.get("age").get(0).name(),
                CoreMatchers.is("age_positive")
        );
    }
}
