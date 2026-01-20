package org.example;

import io.littlehorse.sdk.usertask.annotations.UserTaskField;

public class ApprovalForm {

    @UserTaskField(displayName = "Approved", description = "Check to approve the loan")
    public boolean approved;

    @UserTaskField(displayName = "Comments", description = "Reason for decision", required = false)
    public String comments;
}