package org.example;

import java.util.List;

import io.littlehorse.sdk.common.config.LHConfig;
import io.littlehorse.sdk.common.proto.PutExternalEventDefRequest;
import io.littlehorse.sdk.common.proto.PutUserTaskDefRequest;
import io.littlehorse.sdk.usertask.UserTaskSchema;
import io.littlehorse.sdk.worker.LHTaskWorker;

public class Main {

    public static void main(String[] args) throws Exception {

        // Example 1
        // LHConfig config = new LHConfig();

        // Greeter greeter = new Greeter();
        // LHTaskWorker worker = new LHTaskWorker(greeter, "greet", config);

        // // Registra el TaskDef
        // worker.registerTaskDef();

        // // Registra el Workflow
        // GreetingWorkflow wf = new GreetingWorkflow();
        // wf.getWorkflow().registerWfSpec(config.getBlockingStub());

        // // Inicia el worker
        // System.out.println("Worker iniciado! Presiona Ctrl+C para salir.");
        // worker.start();

        // Example 2

        // LHConfig config = new LHConfig();
        // OrderTasks tasks = new OrderTasks();

        // // Crear workers para cada tarea
        // List<LHTaskWorker> workers = List.of(
        // new LHTaskWorker(tasks, "check-stock", config),
        // new LHTaskWorker(tasks, "process-payment", config),
        // new LHTaskWorker(tasks, "confirm-order", config),
        // new LHTaskWorker(tasks, "notify-out-of-stock", config));

        // // Registrar TaskDefs
        // for (LHTaskWorker worker : workers) {
        // worker.registerTaskDef();
        // }

        // // Registrar Workflow
        // OrderWorkflow wf = new OrderWorkflow();
        // wf.getWorkflow().registerWfSpec(config.getBlockingStub());

        // // Iniciar workers
        // Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        // workers.forEach(LHTaskWorker::close);
        // }));

        // System.out.println("Workers started! Press Ctrl+C to exit.");
        // workers.forEach(LHTaskWorker::start);

        // Example 3

        // LHConfig config = new LHConfig();
        // PaymentTasks tasks = new PaymentTasks();

        // List<LHTaskWorker> workers = List.of(
        // new LHTaskWorker(tasks, "reserve-inventory", config),
        // new LHTaskWorker(tasks, "process-payment", config),
        // new LHTaskWorker(tasks, "release-inventory", config),
        // new LHTaskWorker(tasks, "confirm-shipment", config));

        // for (LHTaskWorker worker : workers) {
        // worker.registerTaskDef();
        // }

        // PaymentWorkflow wf = new PaymentWorkflow();
        // wf.getWorkflow().registerWfSpec(config.getBlockingStub());

        // Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        // workers.forEach(LHTaskWorker::close);
        // }));

        // System.out.println("Workers started! Press Ctrl+C to exit.");
        // workers.forEach(LHTaskWorker::start);

        // Example 4

        // LHConfig config = new LHConfig();
        // LoanTasks tasks = new LoanTasks();

        // // Create task workers
        // List<LHTaskWorker> workers = List.of(
        // new LHTaskWorker(tasks, "evaluate-loan", config),
        // new LHTaskWorker(tasks, "process-loan", config),
        // new LHTaskWorker(tasks, "notify-rejection", config),
        // new LHTaskWorker(tasks, "notify-approval", config));

        // // Register TaskDefs
        // for (LHTaskWorker worker : workers) {
        // worker.registerTaskDef();
        // }

        // // Register UserTaskDef
        // UserTaskSchema schema = new UserTaskSchema(new ApprovalForm(),
        // LoanWorkflow.USER_TASK_NAME);
        // PutUserTaskDefRequest request = schema.compile();
        // config.getBlockingStub().putUserTaskDef(request);
        // System.out.println("UserTaskDef '" + LoanWorkflow.USER_TASK_NAME + "'
        // registered");

        // // Register Workflow
        // LoanWorkflow wf = new LoanWorkflow();
        // wf.getWorkflow().registerWfSpec(config.getBlockingStub());

        // // Start workers
        // Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        // workers.forEach(LHTaskWorker::close);
        // }));

        // System.out.println("Workers started! Press Ctrl+C to exit.");
        // workers.forEach(LHTaskWorker::start);

        // Example 5

        LHConfig config = new LHConfig();
        VerificationTasks tasks = new VerificationTasks();

        // Create task workers
        List<LHTaskWorker> workers = List.of(
                new LHTaskWorker(tasks, "send-verification-request", config),
                new LHTaskWorker(tasks, "approve-customer", config),
                new LHTaskWorker(tasks, "reject-customer", config),
                new LHTaskWorker(tasks, "send-reminder", config));

        // Register TaskDefs
        for (LHTaskWorker worker : workers) {
            worker.registerTaskDef();
        }

        // Register ExternalEventDef
        config.getBlockingStub().putExternalEventDef(
                PutExternalEventDefRequest.newBuilder()
                        .setName(VerificationWorkflow.EVENT_NAME)
                        .build());
        System.out.println("ExternalEventDef '" + VerificationWorkflow.EVENT_NAME + "' registered");

        // Register Workflow
        VerificationWorkflow wf = new VerificationWorkflow();
        wf.getWorkflow().registerWfSpec(config.getBlockingStub());

        // Start workers
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            workers.forEach(LHTaskWorker::close);
        }));

        System.out.println("Workers started! Press Ctrl+C to exit.");
        workers.forEach(LHTaskWorker::start);

    }
}