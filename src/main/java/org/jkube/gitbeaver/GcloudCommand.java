package org.jkube.gitbeaver;

import org.jkube.gitbeaver.util.ExternalProcess;

import java.util.List;
import java.util.Map;

import static org.jkube.gitbeaver.CommandParser.REST;


public class GcloudCommand extends AbstractCommand {
    /**
     * For security reasons we specify absolute path to binaries (instead of adding their location
     * in the path environment variablde, so that one cannot temper with which binary is going top be executed)
     */
    public static final String GCLOUD_BINARY = "/root/google-cloud-sdk/bin/gcloud";

    public GcloudCommand() {
        super("execute gcloud command");
        commandline("GCLOUD *");
        argument(REST, "arguments for the gcloud command");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, Map<String, String> arguments) {
        ExternalProcess gcloud = new ExternalProcess(variables);
        gcloud.command(GCLOUD_BINARY, List.of(arguments.get(REST).split(" ")));
        gcloud
                .dir(workSpace.getWorkdir())
                .successMarker("Created|Copying ")
                .logConsole(GitBeaver.getApplicationLogger(variables).createSubConsole())
                .execute();

    }

}
