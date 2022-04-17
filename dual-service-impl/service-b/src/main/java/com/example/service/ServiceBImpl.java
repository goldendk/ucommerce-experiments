package com.example.service;


public class ServiceBImpl implements ServiceB {

    private final ServiceAProxy serviceA;

    public ServiceBImpl(ServiceAProxy serviceA) {
        this.serviceA = serviceA;
    }

    @Override
    public String doBusiness() {
        int sum = this.serviceA.calculateSum(2, 3);
        System.out.println("The sum is: " + sum);
        return String.valueOf(sum);
    }
}
