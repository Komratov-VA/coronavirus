package com.sber;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.SneakyThrows;

public class InjectPropertyAnnotationObjectConfigurator implements ObjectConfigurator {

    private Map<String,String> propertiesMap;

    @SneakyThrows
    public InjectPropertyAnnotationObjectConfigurator(){
        String path = ClassLoader.getSystemClassLoader().getResource("application.properties").getPath();
        Stream<String> lines = new BufferedReader((new FileReader(path))).lines();
        propertiesMap= lines.map(line -> line.split("=")).collect(
            Collectors.toMap(arr -> arr[0], arr->arr[1]));
    }


    @Override
    @SneakyThrows
    public void configure(Object t, ApplicationContext context) {
        Class<?> imple = t.getClass();
        for (Field field :imple.getDeclaredFields()){
            InjectProperty injectProperty = field.getAnnotation(InjectProperty.class);
            if(injectProperty != null){
                String value = injectProperty.value().isEmpty() ? propertiesMap.get(field.getName()):propertiesMap.get(injectProperty.value());
                field.setAccessible(true);
                field.set(t,value);
            }
        }
    }
}
