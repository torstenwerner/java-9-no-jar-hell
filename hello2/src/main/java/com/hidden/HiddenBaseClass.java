package com.hidden;

public class HiddenBaseClass {
    static {
        final Class<HiddenBaseClass> klass = HiddenBaseClass.class;
        System.out.printf("Loading class %s from class loader %s with parent %s.\n",
                klass, klass.getClassLoader(), klass.getClassLoader().getParent());
    }
}
