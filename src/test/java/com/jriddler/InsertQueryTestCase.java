package com.jriddler;

import com.jriddler.columns.*;
import com.jriddler.columns.jdbc.*;
import com.jriddler.text.InsertQuery;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
        final List<ColumnDefinition> definitions = Arrays.asList(
                new IntColumn("age"),
                new VarCharColumn("name", 10),
                new VarCharColumn("surname", 10),
                new BoolColumn("active"),
                new TimeStampColumn("birthday"),
                new BigIntColumn("id")
        );
        this.insertQuery = new InsertQuery(definitions, TABLE_NAME);
    }

    /**
     * Test build.
     */
    @Test
    public void testBuild() {
        Assert.assertThat(
                this.insertQuery.asString(),
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