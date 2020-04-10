package com.jriddler.attr;


import com.jriddler.attrs.AttributeDefinition;
import com.jriddler.attrs.AttrBuilder;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Types;
import java.time.OffsetDateTime;

/**
 * Test Definition factory.
 */
@SuppressWarnings("MagicNumber")
public final class AttrBuilderTestCase {


    /**
     * Test create int attr.
     */
    @Test
    public void testCreateInt() {
        Assert.assertThat(
                new AttrBuilder(
                        Types.INTEGER,
                        4,
                        "age"
                ).value(),
                CoreMatchers.instanceOf(Integer.class)
        );
    }

    /**
     * Test create long attr.
     */
    @Test
    public void testCreateBigInt() {
        Assert.assertThat(
                new AttrBuilder(
                        Types.BIGINT,
                        8,
                        "id"
                ).value(),
                CoreMatchers.instanceOf(Long.class)
        );
    }

    /**
     * Test create bool attr.
     */
    @Test
    public void testCreateBool() {
        Assert.assertThat(
                new AttrBuilder(
                        Types.BIT,
                        1,
                        "active"
                ).value(),
                CoreMatchers.instanceOf(Boolean.class)
        );
    }

    /**
     * Test create timestamp attr.
     */
    @Test
    public void testCreateTimeStamp() {
        Assert.assertThat(
                new AttrBuilder(
                        Types.TIMESTAMP,
                        35,
                        "birthday"
                ).value(),
                CoreMatchers.instanceOf(OffsetDateTime.class)
        );
    }

    /**
     * Test create string attr.
     */
    @Test
    public void testCreateString() {
        final AttributeDefinition definition = new AttrBuilder(
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
     * Test that will fail cause given type id is not supported.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUnSupportedType() {
        new AttrBuilder(
                Integer.MIN_VALUE,
                100,
                "test"
        ).value();
    }
}
