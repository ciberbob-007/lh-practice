package org.example;

import java.util.List;

import io.littlehorse.sdk.common.config.LHConfig;
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

        LHConfig config = new LHConfig();
        OrderTasks tasks = new OrderTasks();

        // Crear workers para cada tarea
        List<LHTaskWorker> workers = List.of(
                new LHTaskWorker(tasks, "check-stock", config),
                new LHTaskWorker(tasks, "process-payment", config),
                new LHTaskWorker(tasks, "confirm-order", config),
                new LHTaskWorker(tasks, "notify-out-of-stock", config));

        // Registrar TaskDefs
        for (LHTaskWorker worker : workers) {
            worker.registerTaskDef();
        }

        // Registrar Workflow
        OrderWorkflow wf = new OrderWorkflow();
        wf.getWorkflow().registerWfSpec(config.getBlockingStub());

        // Iniciar workers
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            workers.forEach(LHTaskWorker::close);
        }));

        System.out.println("Workers started! Press Ctrl+C to exit.");
        workers.forEach(LHTaskWorker::start);

    }
}