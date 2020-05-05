package com.jriddler.text;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test {@link Help}.
 */
public final class HelpTestCase {

    /**
     * Test that help information has all columns.
     */
    @Test
    public void testHelpInformation() {
        final String help = new Help().asString();
        Assert.assertTrue(help.contains("Supported parameters"));
        Assert.assertTrue(help.contains("-host"));
        Assert.assertTrue(help.contains("-port"));
        Assert.assertTrue(help.contains("-username"));
        Assert.assertTrue(help.contains("-table"));
        Assert.assertTrue(help.contains("-password"));
        Assert.assertTrue(help.contains("-db"));
        Assert.assertTrue(help.contains("-table"));
        Assert.assertTrue(help.contains("-version"));
        Assert.assertTrue(help.contains("-help"));
        Assert.assertTrue(help.contains("-V"));
    }
}
