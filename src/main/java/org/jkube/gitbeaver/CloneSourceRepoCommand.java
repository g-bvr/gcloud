package org.jkube.gitbeaver;

import org.jkube.gitbeaver.util.ExternalProcess;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.jkube.gitbeaver.GcloudCommand.GCLOUD_BINARY;


public class CloneSourceRepoCommand extends AbstractCommand {

    private static final String PROJECT = "project";
    private static final String REPO = "repo";
    private static final String BRANCH = "branch";
    private static final String GIT_BINARY = "git";
    private static final String DEFAULT_BRANCH = "main";

    public CloneSourceRepoCommand() {
        super("clone a gcloud source repo");
        commandlineVariant("GCLOUD CLONE BRANCH "+BRANCH+" OF SOURCE REPO "+REPO+" IN PROJECT "+PROJECT, "clone a specific branch of a source repo");
        commandlineVariant("GCLOUD CLONE SOURCE REPO "+REPO+" IN PROJECT "+PROJECT, "clone main branch of a source repo");
        argument(PROJECT, "the gcp project in which the repository is located");
        argument(REPO, "the name of the gcp source reopsitory");
        argument(BRANCH, "the branch to be checked out");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, Map<String, String> arguments) {
        String project = arguments.get(PROJECT);
        String repo = arguments.get(REPO);
        String branch = arguments.get(BRANCH);
        if (branch == null) {
            branch = DEFAULT_BRANCH;
        }
        cloneRepo(variables, workSpace, project, repo);
        checkoutBranch(variables, workSpace, repo, branch);
    }

    private void cloneRepo(Map<String, String> variables, WorkSpace workSpace, String project, String repo) {
        ExternalProcess gcloud = new ExternalProcess();
        gcloud.command(GCLOUD_BINARY, List.of("source", "repos", "clone", project, repo));
        gcloud
                .dir(workSpace.getWorkdir())
                .successMarker("Project .* repository .* was cloned to .*")
                .logConsole(GitBeaver.getApplicationLogger(variables).createSubConsole())
                .execute();
    }

    private void checkoutBranch(Map<String, String> variables, WorkSpace workSpace, String repo, String branch) {
        ExternalProcess gcloud = new ExternalProcess();
        gcloud.command(GIT_BINARY, List.of("checkout", "-b", branch));
        gcloud
                .dir(workSpace.getWorkdir().resolve(Path.of(repo)))
                .successMarker("Switched to a new branch")
                .logConsole(GitBeaver.getApplicationLogger(variables).createSubConsole())
                .execute();
    }

}
