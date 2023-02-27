package org.jkube.gitbeaver;

import org.jkube.gitbeaver.util.ExternalProcess;
import org.jkube.util.Expect;

import java.util.List;
import java.util.Map;

import static org.jkube.gitbeaver.GcloudCommand.GCLOUD_BINARY;


public class StoreGcloudApiTokenCommand extends AbstractCommand {

    private static final String VARIABLE = "variable";

    public StoreGcloudApiTokenCommand() {
        super("get gcloud api token");
        commandline("STORE GCLOUD API TOKEN IN VARIABLE "+VARIABLE);
        argument(VARIABLE, "name of the variable which is set to api access token");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, Map<String, String> arguments) {
        ExternalProcess gcloud = new ExternalProcess();
        gcloud.command(GCLOUD_BINARY, List.of("auth", "application-default", "print-access-token"));
        List<String> output = gcloud
                .dir(workSpace.getWorkdir())
                .successMarker("Created ")
                .logConsole(GitBeaver.getApplicationLogger(variables).createSubConsole())
                .execute()
                .getOutput();
        Expect.size(output, 1).elseFail("Expected single output line, got "+output.size());
        variables.put(arguments.get(VARIABLE), output.get(0));
    }

}
