package com.jriddler.attr;


import com.jriddler.attrs.AttributeDefinition;
import com.jriddler.attrs.DefinitionFactory;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Types;
import java.time.OffsetDateTime;

/**
 * Test Definition factory.
 */
@SuppressWarnings("MagicNumber")
public final class DefinitionFactoryTest {

    /**
     * Factory to test.
     */
    private DefinitionFactory factory;

    /**
     * Init.
     */
    @Before
    public void init() {
        this.factory = new DefinitionFactory();
    }

    /**
     * Test create int attr.
     */
    @Test
    public void testCreateInt() {
        Assert.assertThat(
                this.factory.create(
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
                this.factory.create(
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
                this.factory.create(
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
                this.factory.create(
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
        final AttributeDefinition definition = this.factory.create(
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
}
