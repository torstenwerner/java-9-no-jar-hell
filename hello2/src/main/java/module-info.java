module com.greetings {
        exports com.greetings;
        provides java.lang.Runnable with com.greetings.Main;
}
