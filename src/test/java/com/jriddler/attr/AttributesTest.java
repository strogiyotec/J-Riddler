package com.jriddler.attr;

import com.jriddler.attrs.AttributeDefinition;
import com.jriddler.attrs.Attributes;
import com.jriddler.sql.DbTest;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public final class AttributesTest extends DbTest {

    @Test
    public void testAttributes() {
        final List<AttributeDefinition> attributes = this.attributes();
        Assert.assertTrue(!attributes.isEmpty());
        Assert.assertThat(attributes.get(0).name(), CoreMatchers.is("id"));
        Assert.assertThat(attributes.get(1).name(), CoreMatchers.is("name"));
        Assert.assertThat(attributes.get(2).name(), CoreMatchers.is("surname"));
        Assert.assertThat(attributes.get(3).name(), CoreMatchers.is("age"));
        Assert.assertThat(attributes.get(4).name(), CoreMatchers.is("active"));
        Assert.assertThat(attributes.get(5).name(), CoreMatchers.is("birthday"));
    }

    private List<AttributeDefinition> attributes() {
        final Iterator<AttributeDefinition> users = new Attributes(
                "users",
                this.jdbcTemplate
        ).iterator();
        final Iterable<AttributeDefinition> iterable = () -> users;
        return StreamSupport
                .stream(
                        iterable.spliterator(),
                        false
                )
                .collect(Collectors.toList());
    }
}
