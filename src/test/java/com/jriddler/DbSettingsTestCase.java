package com.jriddler;

import com.beust.jcommander.JCommander;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link DbSettings}.
 */
@SuppressWarnings("MagicNumber")
public final class DbSettingsTestCase {

    /**
     * Test that DbSettings were created from argc.
     */
    @Test
    public void testSettingsParse() {
        final DbSettings dbSettings = new DbSettings();
        JCommander.newBuilder()
                .addObject(dbSettings)
                .build()
                .parse(
                        "-table", "users",
                        "-host", "localhost",
                        "-port", "5432",
                        "-name", "postgres",
                        "-password", "123"
                );
        Assert.assertThat(
                dbSettings.getPassword(),
                CoreMatchers.is("123")
        );
        Assert.assertThat(
                dbSettings.getUsername(),
                CoreMatchers.is("postgres")
        );
        Assert.assertThat(
                dbSettings.getDbHost(),
                CoreMatchers.is("localhost")
        );
        Assert.assertThat(
                dbSettings.getPort(),
                CoreMatchers.is(5432)
        );
        Assert.assertThat(
                dbSettings.getTable(),
                CoreMatchers.is("users")
        );
    }
}
