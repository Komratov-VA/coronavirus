package com.sber;

public class PolicemanImpl implements Policeman {
    @InjectByType
    private Recommendator recommendator;

    @PostConstruct
    public void init(){
        System.out.println(recommendator.getClass());
    }
    public void makePeopleLeaveRoom() {
        System.out.println("пиф паф киш киш");
    }
}
