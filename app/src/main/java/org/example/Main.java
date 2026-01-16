package org.example;

import io.littlehorse.sdk.common.config.LHConfig;
import io.littlehorse.sdk.worker.LHTaskWorker;

public class Main {

    public static void main(String[] args) throws Exception {
        LHConfig config = new LHConfig();

        Greeter greeter = new Greeter();
        LHTaskWorker worker = new LHTaskWorker(greeter, "greet", config);

        // Registra el TaskDef
        worker.registerTaskDef();

        // Registra el Workflow
        GreetingWorkflow wf = new GreetingWorkflow();
        wf.getWorkflow().registerWfSpec(config.getBlockingStub());

        // Inicia el worker
        System.out.println("Worker iniciado! Presiona Ctrl+C para salir.");
        worker.start();
    }
}