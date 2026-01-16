package org.example;

import io.littlehorse.sdk.wfsdk.Workflow;
import io.littlehorse.sdk.wfsdk.WorkflowThread;

public class OrderWorkflow {

    public static final String WF_NAME = "order-workflow";

    public Workflow getWorkflow() {
        return Workflow.newWorkflow(WF_NAME, this::define);
    }

    public void define(WorkflowThread wf) {
        // Variables de entrada
        var orderId = wf.declareStr("orderId");
        var productId = wf.declareStr("productId");
        var quantity = wf.declareInt("quantity");
        var amount = wf.declareDouble("amount");

        // Variables internas
        var hasStock = wf.declareBool("hasStock");
        var paymentId = wf.declareStr("paymentId");

        // 1. Verificar stock
        var stockResult = wf.execute("check-stock", productId, quantity);
        hasStock.assign(stockResult);

        // 2. Condicional
        wf.doIf(hasStock.isEqualTo(true), ifBlock -> {
            // Hay stock: procesar pago y confirmar
            var payResult = ifBlock.execute("process-payment", orderId, amount);
            paymentId.assign(payResult);
            ifBlock.execute("confirm-order", orderId, paymentId);
        }).doElse(elseBlock -> {
            // Sin stock: notificar
            elseBlock.execute("notify-out-of-stock", productId);
        });
    }
}