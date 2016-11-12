package com.app;

import com.shared.DerivedClass;
import com.shared.StringSupplier;

import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.lang.reflect.Layer;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Main {
    private static final String MODULE_NAME = "com.greetings";

    private static final DerivedClass derived = new DerivedClass();

    /**
     * Create a new {@link java.lang.reflect.Layer} for the module com.greeting on a path. The boot layer will be the
     * parent of the new Layer which allows accessing modules in the boot layer from the module layer e.g. the
     * com.shared module.
     *
     * @param modulePath path where the module is located
     */
    private static Layer createLayer(String modulePath) {
        final ModuleFinder finder = ModuleFinder.of(Paths.get(modulePath));
        final Layer parent = Layer.boot();
        final Configuration newCfg =
                parent.configuration().resolveRequires(finder, ModuleFinder.of(), Set.of(MODULE_NAME));
        return parent.defineModulesWithOneLoader(newCfg, ClassLoader.getSystemClassLoader());
    }

    /**
     * Creates several layers in a batch. A new layer is created for each item in modulePaths.
     */
    private static List<Layer> createAllLayers(String[] modulePaths) {
        return Arrays.stream(modulePaths)
                .map(Main::createLayer)
                .collect(Collectors.toList());
    }

    /**
     * Loads the services of type StringSupplier found in the layer.
     */
    private static ServiceLoader<StringSupplier> loadServices(Layer layer) {
        return ServiceLoader.load(layer, StringSupplier.class);
    }

    /**
     * Execute all services found by the serviceLoader and print the result to stdout. It is exactly 1 service per
     * serviceLoader in this case. But the ServiceLoader API always works with Iterables and that is why we are using
     * streams here.
     */
    private static void executeServices(ServiceLoader<StringSupplier> serviceLoader) {
        StreamSupport.stream(serviceLoader.spliterator(), false)
                .map(StringSupplier::get)
                .forEach(System.out::println);
    }

    /**
     * Load the module com.greetings multiple times from the paths specified on the command line. Create a layer for
     * each path. Load a service of type {@link StringSupplier} for each layer and execute each one.
     * <p/>
     * We are using {@link java.util.ServiceLoader} here because it is type safe, it is easier to use than low level
     * reflection, and it has been improved in Java 9.
     */
    public static void main(String[] args) {
        final List<Layer> layers = createAllLayers(args);
        // All modules are loaded now in the same jvm but in different layers.

        // Loading and executing the services from conflicting module versions now.
        layers.stream()
                .map(Main::loadServices)
                .forEach(Main::executeServices);
    }
}
