package com.app;

import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.lang.reflect.Layer;
import java.nio.file.Paths;
import java.util.List;
import java.util.ServiceLoader;

public class Main {
    private static Layer createLayer(String modulePath, String moduleName) {
        final ModuleFinder finder = ModuleFinder.of(Paths.get(modulePath));
        final Configuration parentCfg = Layer.boot().configuration();
        final Configuration newCfg = parentCfg.resolveRequires(finder, ModuleFinder.empty(), List.of(moduleName));
        return Layer.boot().defineModulesWithOneLoader(newCfg, ClassLoader.getSystemClassLoader());
    }

    public static void main(String[] args) {
        for (String arg : args) {
            final Layer layer = createLayer(arg, "com.greetings");
            final ServiceLoader<Runnable> serviceLoader = ServiceLoader.load(layer, Runnable.class);
            serviceLoader.forEach(Runnable::run);
        }
    }
}
