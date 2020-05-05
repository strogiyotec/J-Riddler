package com.jriddler.text;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test {@link Version}.
 */
public final class VersionTestCase {

    /**
     * Test that version contains version number.
     */
    @Test
    public void testVersionString() {
        Assert.assertThat(
                new Version().asString(),
                CoreMatchers.containsString(Version.VERSION)
        );
    }
}
