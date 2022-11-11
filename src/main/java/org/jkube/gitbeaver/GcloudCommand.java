package org.jkube.gitbeaver;

import org.jkube.gitbeaver.util.ExternalProcess;

import java.util.List;
import java.util.Map;


public class GcloudCommand extends AbstractCommand {
    /**
     * For security reasons we specify absolute path to binaries (instead of adding their location
     * in the path environment variablde, so that one cannot temper with which binary is going top be executed)
     */
    private static final String GCLOUD_BINARY = "/root/google-cloud-sdk/bin/gcloud";

    public GcloudCommand() {
        super(2, null, "gcloud");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, List<String> arguments) {
        ExternalProcess gcloud = new ExternalProcess();
        gcloud.command(GCLOUD_BINARY, arguments);
        gcloud
                .dir(workSpace.getWorkdir())
                .successMarker("Created ")
                .logConsole(GitBeaver.getApplicationLogger(variables).createSubConsole())
                .execute();

    }

}
