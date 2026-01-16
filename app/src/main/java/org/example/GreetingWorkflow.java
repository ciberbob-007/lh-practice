package org.example;

import io.littlehorse.sdk.wfsdk.Workflow;
import io.littlehorse.sdk.wfsdk.WorkflowThread;

public class GreetingWorkflow {

    public static final String WF_NAME = "greeting-workflow";

    public Workflow getWorkflow() {
        return Workflow.newWorkflow(WF_NAME, this::define);
    }

    public void define(WorkflowThread wf) {
        var name = wf.declareStr("name");
        wf.execute("greet", name);
    }
}