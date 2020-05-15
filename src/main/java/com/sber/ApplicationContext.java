package com.sber;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import lombok.Setter;

public class ApplicationContext {
    @Setter
    private ObjectFactory factory;
    private Map<Class,Object> cache = new ConcurrentHashMap<>();
    @Getter
    private Config config;

    public ApplicationContext(Config config){
        this.config = config;

    }

    public <T> T getObject(Class<T> type){
        Class<? extends T> imple = type;

        if(cache.containsKey(type)){
            return (T) cache.get(type);
        }

        if(type.isInterface()){
            imple = config.getImplClass(type);
        }

       T t = factory.createObject(imple);
        if(imple.isAnnotationPresent(Singleton.class)){
            cache.put(type, t);
        }

        return t;
    }

}
