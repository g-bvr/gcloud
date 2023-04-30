package org.jkube.gitbeaver;

import org.jkube.gitbeaver.util.ExternalProcess;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.jkube.gitbeaver.GcloudCommand.GCLOUD_BINARY;


public class ActivateServiceAccountCommand extends AbstractCommand {

    private static final String KEY = "keyfile";
    private static final String KEY_PATH = "/secret/";

    public ActivateServiceAccountCommand() {
        super("activate a gcloud service account");
        commandline("GCLOUD ACTIVATE SERVICE ACCOUNT "+KEY);
        argument(KEY, "the name of the file containing the service account key (must be located under "+KEY_PATH+")");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, Map<String, String> arguments) {
        String keyFile = KEY_PATH+arguments.get(KEY);
        ExternalProcess gcloud = new ExternalProcess();
        gcloud.command(GCLOUD_BINARY, List.of("auth", "activate-service-account", "--key-file", keyFile));
        gcloud
                .dir(workSpace.getWorkdir())
                .successMarker("Activated service account")
                .logConsole(GitBeaver.getApplicationLogger(variables).createSubConsole())
                .execute();
    }

}
