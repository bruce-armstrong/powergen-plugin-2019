package hudson.plugins.powergencompile;

import org.junit.Test;

import hudson.plugins.powergencompile.PowergenCompileBuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Jonathan Zimmerman
 */
public class PowergenCompileBuilderTest {

    @Test
    public void shouldStripQuotedArguments() {
        final String quotedPlatform = "/p:Platform=\"Any CPU\"";
        final String strippedPlatform = "/p:Platform=Any CPU";

        String[] tokenizedArgs = PowergenCompileBuilder.tokenizeArgs(quotedPlatform);
        assertNotNull(tokenizedArgs);
        assertEquals(1, tokenizedArgs.length);
        assertEquals(strippedPlatform, tokenizedArgs[0]);
    }

    @Test
    public void shouldSplitArguments() {
        final String arguments = "/t:Build /p:Configuration=Debug";

        String[] tokenizedArgs = PowergenCompileBuilder.tokenizeArgs(arguments);
        assertNotNull(tokenizedArgs);
        assertEquals(2, tokenizedArgs.length);
        assertEquals("/t:Build", tokenizedArgs[0]);
        assertEquals("/p:Configuration=Debug", tokenizedArgs[1]);
    }

    @Test
    public void endEscapedCharacter() {
        final String oneArgumentsWithEndBackslash = "\\\\RemoteServerName\\OfficialBuilds\\Published\\";
        String[] tokenizedArgs = PowergenCompileBuilder.tokenizeArgs(oneArgumentsWithEndBackslash);
        assertEquals(1, tokenizedArgs.length);
        assertEquals(oneArgumentsWithEndBackslash, tokenizedArgs[0]);
    }

}
