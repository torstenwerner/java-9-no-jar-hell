module com.greetings {
        exports com.greetings;
        requires com.shared;
        provides com.shared.StringSupplier with com.greetings.Main;
}
