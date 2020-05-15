package com.sber;

import java.util.Map;

public class Application {
    public static ApplicationContext run(String packageToScan, Map<Class, Class> ifc2ImplClass){
        JavaConfig javaConfig =  new JavaConfig(packageToScan,ifc2ImplClass);
        ApplicationContext context = new ApplicationContext(javaConfig);
        ObjectFactory objectFactory = new ObjectFactory(context);
        //todo homewort init all singletons which are not lazy
        context.setFactory(objectFactory);
        return context;
    }

}
