package com.jriddler;

import com.jriddler.attrs.*;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class InsertQueryTest {

    private static final String TABLE_NAME = "users";

    private InsertQuery insertQuery;

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

    @Test
    public void build() {
        Assert.assertThat(
                this.insertQuery.build(),
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