package com.jriddler.cli;

import com.beust.jcommander.JCommander;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link UserInput}.
 */
@SuppressWarnings("MagicNumber")
public final class UserInputTestCase {

    /**
     * Test that UserInput was created from argc.
     */
    @Test
    public void testSettingsParse() {
        final UserInput userInput = new UserInput();
        JCommander.newBuilder()
                .addObject(userInput)
                .build()
                .parse(
                        "-table", "users",
                        "-host", "localhost",
                        "-port", "5432",
                        "-name", "postgres",
                        "-password", "123",
                        "-db", "test"
                );
        this.checkMainSettings(userInput);
        Assert.assertTrue(userInput.getUserValues().isEmpty());
    }

    /**
     * Test that UserInput sets default values for host and port.
     */
    @Test
    public void testDefaultSettingsParse() {
        final UserInput userInput = new UserInput();
        JCommander.newBuilder()
                .addObject(userInput)
                .build()
                .parse(
                        "-table", "users",
                        "-name", "postgres",
                        "-password", "123",
                        "-db", "test"
                );
        this.checkMainSettings(userInput);
        Assert.assertTrue(userInput.getUserValues().isEmpty());
    }

    /**
     * Test that custom user_name was used.
     */
    @Test
    public void testParseCustomValue() {
        final UserInput userInput = new UserInput();
        JCommander.newBuilder()
                .addObject(userInput)
                .build()
                .parse(
                        "-table", "users",
                        "-name", "postgres",
                        "-password", "123",
                        "-db", "test",
                        "-Vuser_name=Almas"
                );
        this.checkMainSettings(userInput);
        Assert.assertThat(
                userInput.getUserValues().get("user_name"),
                CoreMatchers.is("Almas")
        );
    }

    /**
     * Check UserInput main params.
     * Port,Host,User,Password,Table,DbName
     *
     * @param userInput UserInput
     */
    private void checkMainSettings(final UserInput userInput) {
        Assert.assertThat(
                userInput.getPassword(),
                CoreMatchers.is("123")
        );
        Assert.assertThat(
                userInput.getUsername(),
                CoreMatchers.is("postgres")
        );
        Assert.assertThat(
                userInput.getHost(),
                CoreMatchers.is("localhost")
        );
        Assert.assertThat(
                userInput.getPort(),
                CoreMatchers.is(5432)
        );
        Assert.assertThat(
                userInput.getTable(),
                CoreMatchers.is("users")
        );
        Assert.assertThat(
                userInput.getDbName(),
                CoreMatchers.is("test")
        );
    }
}
