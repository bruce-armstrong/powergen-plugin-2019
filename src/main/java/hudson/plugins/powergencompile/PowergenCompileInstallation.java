/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014, Kyle Sweeney, Gregory Boissinot and other contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package hudson.plugins.powergencompile;

import hudson.EnvVars;
import hudson.Extension;
import hudson.Util;
import hudson.model.EnvironmentSpecific;
import hudson.model.Node;
import hudson.model.TaskListener;
import hudson.slaves.NodeSpecific;
import hudson.tools.ToolDescriptor;
import hudson.tools.ToolInstallation;
import jenkins.model.Jenkins;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.IOException;

/**
 * @author Gregory Boissinot
 */
public final class PowergenCompileInstallation extends ToolInstallation implements NodeSpecific<PowergenCompileInstallation>, EnvironmentSpecific<PowergenCompileInstallation> {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8559121341366443706L;
	private final String defaultArgs;

    @DataBoundConstructor
    public PowergenCompileInstallation(String name, String home, String defaultArgs) {
        super(name, home, null);
        this.defaultArgs = Util.fixEmpty(defaultArgs);
    }

    @Override
    public PowergenCompileInstallation forNode(Node node, TaskListener log) throws IOException, InterruptedException {
        return new PowergenCompileInstallation(getName(), translateFor(node, log), getDefaultArgs());
    }

    @Override
    public PowergenCompileInstallation forEnvironment(EnvVars environment) {
        return new PowergenCompileInstallation(getName(), environment.expand(getHome()), getDefaultArgs());
    }

    public String getDefaultArgs() {
        return this.defaultArgs;
    }

    @Extension @Symbol("powergencompile")
    public static class DescriptorImpl extends ToolDescriptor<PowergenCompileInstallation> {

        @Override
        public String getDisplayName() {
            return "PowergenCompile";
        }

        @Override
        public PowergenCompileInstallation[] getInstallations() {
            return getDescriptor().getInstallations();
        }

        @Override
        public void setInstallations(PowergenCompileInstallation... installations) {
            getDescriptor().setInstallations(installations);
        }
        
        private PowergenCompileBuilder.DescriptorImpl getDescriptor() {
            Jenkins jenkins = Jenkins.getInstance();
            if (jenkins != null && jenkins.getDescriptorByType(PowergenCompileBuilder.DescriptorImpl.class) != null) {
                return jenkins.getDescriptorByType(PowergenCompileBuilder.DescriptorImpl.class);
            } else {
                // To stick with current behavior and meet findbugs requirements
                throw new NullPointerException(jenkins == null ? "Jenkins instance is null" : "PowergenCompileBuilder.DescriptorImpl is null");
            }
        }

    }

}
