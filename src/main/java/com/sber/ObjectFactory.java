package com.sber;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lombok.Setter;
import lombok.SneakyThrows;

public class ObjectFactory {
    private List<ObjectConfigurator> configurators = new ArrayList<>();
    private List<ProxyConfigurator> proxyConfigurators = new ArrayList<>();
    @Setter
    private final ApplicationContext context;

    @SneakyThrows
    public ObjectFactory(ApplicationContext context){
        this.context = context;
        for (Iterator<Class<? extends ObjectConfigurator>> it = context.getConfig().getScanner()
            .getSubTypesOf(ObjectConfigurator.class).iterator(); it.hasNext(); ) {
            Class<? extends ObjectConfigurator> aClass = it.next();
            configurators.add(aClass.getDeclaredConstructor().newInstance());
        }
        for (Iterator<Class<? extends ProxyConfigurator>> it = context.getConfig().getScanner()
            .getSubTypesOf(ProxyConfigurator.class).iterator(); it.hasNext(); ) {
            Class<? extends ProxyConfigurator> aClass = it.next();
            proxyConfigurators.add(aClass.getDeclaredConstructor().newInstance());
        }
    }

    @SneakyThrows
    public <T> T createObject(Class<T> impleClass) {

        T t = create(impleClass);

        configure(t);

        invokeInit(impleClass, t);

        t = wrapWithProxyIfNeeded(impleClass, t);

        return t;
    }

    private <T> T wrapWithProxyIfNeeded(Class<T> impleClass, T t) {
        for (ProxyConfigurator proxyConfigurator : proxyConfigurators){
            t = (T)proxyConfigurator.replaceWithProxyIfNeeded(t, impleClass);
        }
        return t;
    }

    private <T> void invokeInit(Class<T> impleClass, T t)
        throws IllegalAccessException, InvocationTargetException {
        for (Method method : impleClass.getMethods()){
            if(method.isAnnotationPresent(PostConstruct.class)){
                method.invoke(t);
            }
        }
    }

    private <T> void configure(T t) {
        configurators.forEach(objectConfigurator -> objectConfigurator.configure(t, context));
    }

    private <T> T create(Class<T> impleClass)
        throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return impleClass.getDeclaredConstructor().newInstance();
    }
}
