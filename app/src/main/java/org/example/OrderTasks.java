package org.example;

import io.littlehorse.sdk.worker.LHTaskMethod;

public class OrderTasks {

    @LHTaskMethod("check-stock")
    public boolean checkStock(String productId, int quantity) {
        // Simula verificación de stock
        System.out.println("Checking stock for " + productId + ", qty: " + quantity);
        return quantity <= 10; // hay stock si piden 10 o menos
    }

    @LHTaskMethod("process-payment")
    public String processPayment(String orderId, double amount) {
        System.out.println("Processing payment for order " + orderId + ": $" + amount);
        return "PAY-" + System.currentTimeMillis();
    }

    @LHTaskMethod("confirm-order")
    public String confirmOrder(String orderId, String paymentId) {
        System.out.println("Order " + orderId + " confirmed with payment " + paymentId);
        return "Order " + orderId + " confirmed!";
    }

    @LHTaskMethod("notify-out-of-stock")
    public String notifyOutOfStock(String productId) {
        System.out.println("Product " + productId + " is out of stock");
        return "Sorry, " + productId + " is not available";
    }
}