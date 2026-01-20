package org.example;

import io.littlehorse.sdk.wfsdk.Workflow;
import io.littlehorse.sdk.wfsdk.WorkflowThread;

public class PaymentWorkflow {

    public static final String WF_NAME = "payment-workflow";

    public Workflow getWorkflow() {
        return Workflow.newWorkflow(WF_NAME, this::define);
    }

    public void define(WorkflowThread wf) {
        // Input variables
        var orderId = wf.declareStr("orderId");
        var productId = wf.declareStr("productId");
        var quantity = wf.declareInt("quantity");
        var amount = wf.declareDouble("amount");

        // Internal variables
        var reservationId = wf.declareStr("reservationId");
        var paymentId = wf.declareStr("paymentId");

        // 1. Reserve inventory
        var reserveResult = wf.execute("reserve-inventory", productId, quantity);
        reservationId.assign(reserveResult);

        // 2. Process payment with retries and timeout
        var payResult = wf.execute("process-payment", orderId, amount)
                // .withRetries(1)
                .timeout(5); // 5 seconds timeout

        // 3. Handle payment failure - run compensation
        // wf.handleError(payResult, handler -> {
        // handler.execute("release-inventory", reservationId);
        // });

        // 4. If we get here, payment succeeded - assign and confirm
        paymentId.assign(payResult);
        wf.execute("confirm-shipment", orderId, reservationId);
    }
}