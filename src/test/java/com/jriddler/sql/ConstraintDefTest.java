package com.jriddler.sql;

import com.jriddler.contraint.Constraint;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public final class ConstraintDefTest extends DbTest {

    private ConstraintDef constraintDef;

    @Before
    public void init() {
        this.constraintDef = new ConstraintDef(
                "users",
                this.jdbcTemplate
        );
    }

    @Test
    public void testFetchConstraints(){
        final Map<String, List<Constraint>> constraints = this.constraintDef.perform();
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
