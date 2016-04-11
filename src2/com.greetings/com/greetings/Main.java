package com.greetings;

public class Main implements Runnable {
    @Override
    public void run() {
        Object name = "main2222";
        System.out.format("Greetings from %s!\n", name);
    }
}
