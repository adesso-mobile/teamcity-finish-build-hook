package de.ams.itoperations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.intellij.openapi.diagnostic.Logger;

import jetbrains.buildServer.agent.AgentLifeCycleAdapter;
import jetbrains.buildServer.agent.AgentRunningBuild;
import jetbrains.buildServer.agent.BuildAgent;
import jetbrains.buildServer.agent.BuildFinishedStatus;
import jetbrains.buildServer.util.EventDispatcher;

public class FinishBuildExecutor extends AgentLifeCycleAdapter {
    private static final Logger LOG = Logger.getInstance(FinishBuildExecutor.class.getName());

    public FinishBuildExecutor(BuildAgent buildAgent, EventDispatcher<AgentLifeCycleAdapter> eventDispatcher) {
        eventDispatcher.addListener(this);

        LOG.info("--------------------------------------------------------------------------------");
        LOG.info("--------------------------------------------------------------------------------");
        LOG.info("--------------------------------------------------------------------------------");
        LOG.info("-----------------------Loading Finish Build Hook Plugin-------------------------");
        LOG.info("--------------------------------------------------------------------------------");
        LOG.info("--------------------------------------------------------------------------------");
        LOG.info("--------------------------------------------------------------------------------");
    }

    @Override
    public void buildFinished(AgentRunningBuild build, BuildFinishedStatus buildStatus) {
        final String homeDirectory = System.getProperty("user.home");

        String cmd = homeDirectory + "/.finish_build_hook";
        Runtime run = Runtime.getRuntime();

        LOG.info("Executing: " + cmd);
        try {
            Process process = run.exec(cmd);
            process.waitFor();

            BufferedReader buf = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line = "";

            while ((line = buf.readLine()) != null) {
                LOG.info(line);
            }
        } catch (IOException e) {
            LOG.error("Something went wrong with executing the finish build hook.");
        } catch (InterruptedException e) {
            LOG.error("Waiting for finish build hook to finish was interrupted");
        }
    }
}
