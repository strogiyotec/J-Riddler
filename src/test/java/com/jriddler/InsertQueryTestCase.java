package com.jriddler;

import com.jriddler.attrs.*;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Test insert query.
 */
@SuppressWarnings("MagicNumber")
public final class InsertQueryTestCase {

    /**
     * Table name.
     */
    private static final String TABLE_NAME = "users";

    /**
     * Insert query.
     */
    private InsertQuery insertQuery;

    /**
     * Init.
     */
    @Before
    public void init() {
        final List<AttributeDefinition> definitions = Arrays.asList(
                new IntAttr("age"),
                new VarCharAttr("name", 10),
                new VarCharAttr("surname", 10),
                new BoolAttr("active"),
                new TimeStampAttr("birthday"),
                new BigIntAttr("id")
        );
        this.insertQuery = new InsertQuery(definitions, TABLE_NAME);
    }

    /**
     * Test build.
     */
    @Test
    public void testBuild() {
        Assert.assertThat(
                this.insertQuery.create(),
                CoreMatchers.is(
                        String.join(
                                "\n",
                                "INSERT INTO " + TABLE_NAME,
                                "(age,name,surname,active,birthday,id)",
                                "VALUES ",
                                "(?,?,?,?,?,?)\n"
                        )
                )
        );
    }
}