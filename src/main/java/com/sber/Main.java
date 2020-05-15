package com.sber;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = Application.run("com.sber", new HashMap<>(Map.of(Policeman.class,PolicemanImpl.class)));
        CoronaDesinfector coronaDesinfector = applicationContext.getObject(CoronaDesinfector.class);
        coronaDesinfector.start(new Room());
    }
}
