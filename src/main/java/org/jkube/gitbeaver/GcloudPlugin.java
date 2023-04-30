package org.jkube.gitbeaver;

import org.jkube.gitbeaver.plugin.SimplePlugin;

import java.util.Arrays;
import java.util.List;

public class GcloudPlugin extends SimplePlugin {

    public GcloudPlugin() {
        super("Allows execution of GCP operations using the Googlw gcloud cli",
                ActivateServiceAccountCommand.class,
                BigQueryCommand.class,
                StoreGcloudApiTokenCommand.class,
                CloneSourceRepoCommand.class,
                GcloudCommand.class // must be last because it matches of the specialized commandlines
        );
    }

    @Override
    public List<String> getInstallationScript() {
        return Arrays.asList(
                "apk add --no-cache --update which bash python3",
                "curl -sSL -o install.sh https://sdk.cloud.google.com",
                "bash install.sh"
        );
    }

}
