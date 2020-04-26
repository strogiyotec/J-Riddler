package com.jriddler.attr;

import com.jriddler.TestDbInstance;
import com.jriddler.attrs.PrimaryKeys;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Test {@link com.jriddler.attrs.PrimaryKeys}.
 */
public final class PrimaryKeysTestCase extends TestDbInstance {

    /**
     * Test that pk from user table is correct.
     */
    @Test
    public void testUserPrimaryKey() {
        final List<String> users = new PrimaryKeys("users", TestDbInstance.datasource);
        Assert.assertThat(users.size(), CoreMatchers.is(1));
        Assert.assertThat(users.get(0), CoreMatchers.equalTo("id"));
    }
}
