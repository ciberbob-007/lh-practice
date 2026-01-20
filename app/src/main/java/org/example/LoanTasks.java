package org.example;

import io.littlehorse.sdk.worker.LHTaskMethod;

public class LoanTasks {

    @LHTaskMethod("evaluate-loan")
    public String evaluateLoan(String customerId, double amount) {
        System.out.println("Evaluating loan for customer " + customerId + ": $" + amount);

        // Simple evaluation logic
        if (amount <= 1000) {
            return "AUTO_APPROVED";
        } else if (amount <= 5000) {
            return "LOW_RISK";
        } else {
            return "HIGH_RISK";
        }
    }

    @LHTaskMethod("process-loan")
    public String processLoan(String customerId, double amount) {
        System.out.println("Processing loan for customer " + customerId + ": $" + amount);
        return "LOAN-" + System.currentTimeMillis();
    }

    @LHTaskMethod("notify-rejection")
    public void notifyRejection(String customerId, String reason) {
        System.out.println("Loan REJECTED for customer " + customerId + ": " + reason);
    }

    @LHTaskMethod("notify-approval")
    public void notifyApproval(String customerId, String loanId) {
        System.out.println("Loan APPROVED for customer " + customerId + ": " + loanId);
    }
}