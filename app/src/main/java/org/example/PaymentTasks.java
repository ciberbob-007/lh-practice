package org.example;

import io.littlehorse.sdk.worker.LHTaskMethod;

public class PaymentTasks {

    @LHTaskMethod("reserve-inventory")
    public String reserveInventory(String productId, int quantity) {
        System.out.println("Reserving " + quantity + " units of " + productId);
        return "RESERVATION-" + System.currentTimeMillis();
    }

    @LHTaskMethod("process-payment")
    public String processPayment(String orderId, double amount) throws InterruptedException {
        // Random entre 1 y 5 segundos
        int waitTime = (int) (Math.random() * 5) + 1;

        System.out.println("Payment attempt for order " + orderId + " - waiting " + waitTime + "s...");
        Thread.sleep(waitTime * 1000);

        // Si tardó más de 2 segundos, falla
        if (waitTime > 2) {
            System.out.println("Payment FAILED for order " + orderId + ": took too long (" + waitTime + "s)");
            throw new RuntimeException("Payment failed: took too long (" + waitTime + "s)");
        }

        System.out.println("Payment successful for order " + orderId);
        return "PAY-" + System.currentTimeMillis();
    }

    @LHTaskMethod("release-inventory")
    public String releaseInventory(String reservationId) {
        System.out.println("Releasing inventory for reservation " + reservationId);
        return "Released " + reservationId;
    }

    @LHTaskMethod("confirm-shipment")
    public String confirmShipment(String orderId, String reservationId) {
        System.out.println("Shipping order " + orderId + " with reservation " + reservationId);
        return "SHIP-" + System.currentTimeMillis();
    }
}