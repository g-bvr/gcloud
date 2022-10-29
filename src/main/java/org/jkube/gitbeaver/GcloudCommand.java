package org.jkube.gitbeaver;

import org.jkube.gitbeaver.applicationlog.DefaultLogConsole;
import org.jkube.gitbeaver.util.ExternalProcess;

import java.util.List;
import java.util.Map;

public class GcloudCommand extends AbstractCommand {
    protected GcloudCommand() {
        super(2, null, "gcloud");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, List<String> arguments) {
        ExternalProcess gcloud = new ExternalProcess();
        gcloud.command("gcloud", arguments);
        gcloud
                .dir(workSpace.getWorkdir())
                .successMarker("Cloning into ")
                .logConsole(GitBeaver.getApplicationLogger(variables).createSubConsole())
                .execute();

    }
}
