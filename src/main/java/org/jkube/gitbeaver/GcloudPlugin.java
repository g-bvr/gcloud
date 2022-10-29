package org.jkube.gitbeaver;

import org.jkube.gitbeaver.plugin.SimplePlugin;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GcloudPlugin extends SimplePlugin {

    public GcloudPlugin() {
        super(
                GcloudCommand.class
        );
    }

    @Override
    public List<String> getInstallationScript() {
        return Arrays.asList(
                "apk add --no-cache --update which bash",
                "curl -sSL -o install.sh https://sdk.cloud.google.com",
                "bash install.sh"
        );
    }

}
