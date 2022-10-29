package org.jkube.gitbeaver;

import org.jkube.gitbeaver.plugin.SimplePlugin;

public class GcloudPlugin extends SimplePlugin {

    public GcloudPlugin() {
        super(
                GcloudCommand.class
        );
    }

}
