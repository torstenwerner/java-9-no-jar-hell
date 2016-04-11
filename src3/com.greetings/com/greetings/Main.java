package com.greetings;

public class Main implements Runnable {
    @Override
    public void run() {
        Object name = "main3333333";
        System.out.format("Greetings from %s!\n", name);
    }
}
