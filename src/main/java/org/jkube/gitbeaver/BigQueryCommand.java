package org.jkube.gitbeaver;

import org.jkube.gitbeaver.util.ExternalProcess;

import java.util.List;
import java.util.Map;

import static org.jkube.gitbeaver.CommandParser.REST;


public class BigQueryCommand extends AbstractCommand {
    /**
     * For security reasons we specify absolute path to binaries (instead of adding their location
     * in the path environment variablde, so that one cannot temper with which binary is going top be executed)
     */
    public static final String BQ_SCRIPT = "/root/google-cloud-sdk/bin/bq";

    public BigQueryCommand() {
        super("execute big query command");
        commandline("BIGQUERY *");
        argument(REST, "arguments for the bq command");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, Map<String, String> arguments) {
        ExternalProcess gcloud = new ExternalProcess();
        gcloud.command(BQ_SCRIPT, List.of(arguments.get(REST).split(" ")));
        gcloud
                .dir(workSpace.getWorkdir())
                .successMarker("Created ")
                .logConsole(GitBeaver.getApplicationLogger(variables).createSubConsole())
                .execute();

    }

}
