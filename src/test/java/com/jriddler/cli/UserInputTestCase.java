package com.jriddler.cli;

import com.beust.jcommander.JCommander;
import com.jriddler.cli.UserInput;
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
        Assert.assertTrue(userInput.getUserAttributes().isEmpty());
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
        Assert.assertTrue(userInput.getUserAttributes().isEmpty());
    }

    /**
     * Test that dynamic attrs were created.
     */
    @Test
    public void testDynamicTableAttrs() {
        final UserInput userInput = new UserInput();
        JCommander.newBuilder()
                .addObject(userInput)
                .build()
                .parse(
                        "-table", "users",
                        "-name", "postgres",
                        "-password", "123",
                        "-db", "test",
                        "-attrs", "user_name:Almas"
                );
        this.checkMainSettings(userInput);
        Assert.assertThat(
                userInput.getUserAttributes().get(0).getKey(),
                CoreMatchers.is("user_name")
        );
        Assert.assertThat(
                userInput.getUserAttributes().get(0).getValue(),
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
                userInput.getDbHost(),
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
