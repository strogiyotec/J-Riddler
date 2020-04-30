package com.jriddler.columns.fk;

import com.jriddler.TestDbInstance;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Test {@link ForeignKeysBuilder}.
 */
public final class ForeignKeysBuilderTestCase extends TestDbInstance {

    /**
     * Test that table has foreign keys.
     *
     * @throws SQLException If failed
     */
    @Test
    public void test() throws SQLException {
        final ForeignKeysBuilder foreignKeys =
                new ForeignKeysBuilder(
                        TestDbInstance.datasource,
                        "user_to_item"
                );
        Assert.assertTrue(foreignKeys.hasKeys());
        final List<ForeignKey> keys = fromIterable(foreignKeys);
        Assert.assertThat(keys.size(), CoreMatchers.is(2));

        Assert.assertThat(
                keys.get(0).fkColumnName(),
                CoreMatchers.is("item_id")
        );
        Assert.assertThat(
                keys.get(0).pkColumnName(),
                CoreMatchers.is("id")
        );
        Assert.assertThat(
                keys.get(0).pkTableName(),
                CoreMatchers.is("items")
        );

        Assert.assertThat(
                keys.get(1).fkColumnName(),
                CoreMatchers.is("user_id")
        );
        Assert.assertThat(
                keys.get(1).pkColumnName(),
                CoreMatchers.is("id")
        );
        Assert.assertThat(
                keys.get(1).pkTableName(),
                CoreMatchers.is("users")
        );
    }

    /**
     * Create list from iterable.
     *
     * @param iterable Iterable
     * @return List
     */
    private static List<ForeignKey> fromIterable(
            final Iterable<ForeignKey> iterable
    ) {
        final List<ForeignKey> foreignKeys = new ArrayList<>(16);
        iterable.forEach(foreignKeys::add);
        return foreignKeys;
    }
}
