package org.example;

import io.littlehorse.sdk.worker.LHTaskMethod;

public class Greeter {

    @LHTaskMethod("greet")
    public String greet(String name) {
        return "Hola, " + name + "!";
    }
}