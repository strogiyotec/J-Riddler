package com.jriddler.columns;


import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Types;
import java.time.OffsetDateTime;

/**
 * Test Definition factory.
 */
@SuppressWarnings("MagicNumber")
public final class ColumnDefinitionBuilderTestCase {


    /**
     * Test create int columns.
     */
    @Test
    public void testCreateInt() {
        Assert.assertThat(
                new ColumnDefinitionBuilder(
                        Types.INTEGER,
                        4,
                        "age"
                ).value(),
                CoreMatchers.instanceOf(Integer.class)
        );
    }

    /**
     * Test create long columns.
     */
    @Test
    public void testCreateBigInt() {
        Assert.assertThat(
                new ColumnDefinitionBuilder(
                        Types.BIGINT,
                        8,
                        "id"
                ).value(),
                CoreMatchers.instanceOf(Long.class)
        );
    }

    /**
     * Test create bool columns.
     */
    @Test
    public void testCreateBool() {
        Assert.assertThat(
                new ColumnDefinitionBuilder(
                        Types.BIT,
                        1,
                        "active"
                ).value(),
                CoreMatchers.instanceOf(Boolean.class)
        );
    }

    /**
     * Test create timestamp columns.
     */
    @Test
    public void testCreateTimeStamp() {
        Assert.assertThat(
                new ColumnDefinitionBuilder(
                        Types.TIMESTAMP,
                        35,
                        "birthday"
                ).value(),
                CoreMatchers.instanceOf(OffsetDateTime.class)
        );
    }

    /**
     * Test create string columns.
     */
    @Test
    public void testCreateString() {
        final ColumnDefinition definition = new ColumnDefinitionBuilder(
                Types.VARCHAR,
                10,
                "name"
        );
        Assert.assertThat(
                definition.value(),
                CoreMatchers.instanceOf(String.class)
        );
        Assert.assertThat(
                definition.value().toString().length(),
                CoreMatchers.is(10)
        );
    }

    /**
     * Test that will fail cause  type with given id is not supported.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUnSupportedType() {
        new ColumnDefinitionBuilder(
                Integer.MIN_VALUE,
                100,
                "test"
        ).value();
    }
}
