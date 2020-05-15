package com.sber;

import java.util.Map;
import java.util.Set;
import lombok.Getter;
import org.reflections.Reflections;

public class JavaConfig implements Config {

    @Getter
    private Reflections scanner;
    private Map<Class, Class> ifc2ImpClass;

    public JavaConfig(String pakageToScan, Map<Class, Class> ifc2ImpClass){
        this.scanner = new Reflections(pakageToScan);
        this.ifc2ImpClass =ifc2ImpClass;
    }
    @Override
    public <T> Class<? extends T> getImplClass(Class<T> ifc) {
        return ifc2ImpClass.computeIfAbsent(ifc, aClass -> {
            Set<Class<? extends T>> classes = scanner.getSubTypesOf(ifc);
            if (classes.size() != 1) {
                throw new RuntimeException(ifc + "has 0 or more than one iml");
            }
            return classes.iterator().next();
        });
    }
}
