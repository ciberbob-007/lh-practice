package org.example;

import io.littlehorse.sdk.worker.LHTaskMethod;

public class VerificationTasks {

    @LHTaskMethod("send-verification-request")
    public String sendVerificationRequest(String customerId, String email) {
        System.out.println("Sending verification request to " + email + " for customer " + customerId);
        // In real life, this would call an external API (Twilio, SendGrid, etc.)
        return "VERIF-" + System.currentTimeMillis();
    }

    @LHTaskMethod("approve-customer")
    public void approveCustomer(String customerId) {
        System.out.println("Customer " + customerId + " APPROVED - verification successful");
    }

    @LHTaskMethod("reject-customer")
    public void rejectCustomer(String customerId, String reason) {
        System.out.println("Customer " + customerId + " REJECTED - " + reason);
    }

    @LHTaskMethod("send-reminder")
    public void sendReminder(String customerId, String email) {
        System.out.println("Sending reminder to " + email + " for customer " + customerId);
    }
}