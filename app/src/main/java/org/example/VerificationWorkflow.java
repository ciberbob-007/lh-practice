package org.example;

import io.littlehorse.sdk.wfsdk.Workflow;
import io.littlehorse.sdk.wfsdk.WorkflowThread;

public class VerificationWorkflow {

    public static final String WF_NAME = "verification-workflow";
    public static final String EVENT_NAME = "verification-result";

    public Workflow getWorkflow() {
        return Workflow.newWorkflow(WF_NAME, this::define);
    }

    public void define(WorkflowThread wf) {
        // Input variables
        var customerId = wf.declareStr("customerId");
        var email = wf.declareStr("email");

        // Internal variables
        var requestId = wf.declareStr("requestId");
        var isVerified = wf.declareBool("isVerified");

        // 1. Send verification request
        var sendResult = wf.execute("send-verification-request", customerId, email);
        requestId.assign(sendResult);

        // 2. Wait for external event (workflow PAUSES here)
        // Timeout after 60 seconds
        var eventOutput = wf.waitForEvent(EVENT_NAME).timeout(60);

        // 3. Handle timeout (no response received)
        wf.handleError(eventOutput, handler -> {
            handler.execute("send-reminder", customerId, email);
            handler.execute("reject-customer", customerId, "Verification timeout");
        });

        // 4. Extract result from event payload
        isVerified.assign(eventOutput.jsonPath("$.verified"));

        // 5. Branch based on verification result
        wf.doIf(isVerified.isEqualTo(true), approvedBlock -> {
            approvedBlock.execute("approve-customer", customerId);
        }).doElse(rejectedBlock -> {
            rejectedBlock.execute("reject-customer", customerId, "Verification failed");
        });
    }
}