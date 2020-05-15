package com.sber;

import java.lang.reflect.Field;
import lombok.SneakyThrows;

public class InjectByTypeAnnotationObjectConfiguratorImpl implements ObjectConfigurator {

    @Override
    @SneakyThrows
    public void configure(Object t, ApplicationContext context) {
        for(Field field : t.getClass().getDeclaredFields()){
            if(field.isAnnotationPresent(InjectByType.class)){
                field.setAccessible(true);
                Object object = context.getObject(field.getType());
                field.set(t,object);
            }
        }
    }
}
