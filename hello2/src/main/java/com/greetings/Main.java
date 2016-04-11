package com.greetings;

public class Main implements Runnable {
    @Override
    public void run() {
        System.out.println("Greetings from version TWO!");
    }

    /**
     * The main function is provided here only for testing this module in isolation.
     */
    public static void main(String[] args) {
        new Main().run();
    }
}
