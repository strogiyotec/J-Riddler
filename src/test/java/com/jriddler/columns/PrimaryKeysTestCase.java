package com.jriddler.columns;

import com.jriddler.TestDbInstance;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Test {@link com.jriddler.columns.PrimaryKeys}.
 */
public final class PrimaryKeysTestCase extends TestDbInstance {

    /**
     * Test that pk from user table is correct.
     */
    @Test
    public void testUserPrimaryKey() {
        final List<String> users = fromIterable(
                new PrimaryKeys("users", TestDbInstance.datasource)
        );
        Assert.assertThat(users.size(), CoreMatchers.is(1));
        Assert.assertThat(users.get(0), CoreMatchers.equalTo("id"));
    }

    /**
     * Create list from iterable.
     *
     * @param iterable Iterable
     * @return List
     */
    private static List<String> fromIterable(final Iterable<String> iterable) {
        final List<String> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }
}
