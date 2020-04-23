package hudson.plugins.powergencompile;

import hudson.Extension;
import hudson.MarkupText;
import hudson.console.ConsoleAnnotationDescriptor;
import hudson.console.ConsoleAnnotator;
import hudson.console.ConsoleNote;
import hudson.plugins.powergencompile.Messages;

import org.jenkinsci.Symbol;

import java.util.regex.Pattern;

/**
 * Annotation for PBC and CSC error messages
 */
@SuppressWarnings("rawtypes")
public class PowergenCompileErrorNote extends ConsoleNote {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7256681529270905628L;
	/** Pattern to identify error messages */
    public final static Pattern PATTERN = Pattern.compile("(.*)[Ee]rror\\s(([A-Z]*)\\d+){0,1}:\\s(.*)");
    
    public PowergenCompileErrorNote() {
    }

	@Override
    public ConsoleAnnotator annotate(Object context, MarkupText text, int charPos) {
        text.addMarkup(0, text.length(), "<span class=error-inline>", "</span>");
        return null;
    }

    @Extension @Symbol("powergencompileError")
    public static final class DescriptorImpl extends ConsoleAnnotationDescriptor {

        @Override
        public String getDisplayName() {
            return Messages.PowergenCompileBuilder_ErrorNoteDescription();
        }
    }
}
