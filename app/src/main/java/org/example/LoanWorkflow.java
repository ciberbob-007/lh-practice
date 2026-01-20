package org.example;

import io.littlehorse.sdk.wfsdk.Workflow;
import io.littlehorse.sdk.wfsdk.WorkflowThread;

public class LoanWorkflow {

    public static final String WF_NAME = "loan-workflow";
    public static final String USER_TASK_NAME = "approve-loan";

    public Workflow getWorkflow() {
        return Workflow.newWorkflow(WF_NAME, this::define);
    }

    public void define(WorkflowThread wf) {
        // Input variables
        var customerId = wf.declareStr("customerId");
        var amount = wf.declareDouble("amount");

        // Internal variables
        var riskLevel = wf.declareStr("riskLevel");
        var loanId = wf.declareStr("loanId");
        var approved = wf.declareBool("approved");

        // 1. Evaluate loan risk
        var evalResult = wf.execute("evaluate-loan", customerId, amount);
        riskLevel.assign(evalResult);

        // 2. If high risk, require human approval
        wf.doIf(riskLevel.isEqualTo("HIGH_RISK"), highRiskBlock -> {

            // Assign user task to "supervisors" group
            var userTaskOutput = highRiskBlock.assignUserTask(USER_TASK_NAME, null, "supervisors");

            // Extract approval decision from form
            approved.assign(userTaskOutput.jsonPath("$.approved"));

            // 3. Branch based on human decision
            highRiskBlock.doIf(approved.isEqualTo(true), approvedBlock -> {
                var processResult = approvedBlock.execute("process-loan", customerId, amount);
                loanId.assign(processResult);
                approvedBlock.execute("notify-approval", customerId, loanId);
            }).doElse(rejectedBlock -> {
                rejectedBlock.execute("notify-rejection", customerId, "Supervisor rejected");
            });

        }).doElse(autoBlock -> {
            // Auto-approve low risk loans
            var processResult = autoBlock.execute("process-loan", customerId, amount);
            loanId.assign(processResult);
            autoBlock.execute("notify-approval", customerId, loanId);
        });
    }
}