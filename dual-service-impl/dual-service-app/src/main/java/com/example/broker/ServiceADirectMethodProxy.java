package com.example.broker;

import com.example.service.ServiceA;
import com.example.service.ServiceAProxy;

public class ServiceADirectMethodProxy implements ServiceAProxy {
    private final ServiceA service;

    public ServiceADirectMethodProxy(ServiceA service) {
        this.service = service;
    }

    @Override
    public int calculateSum(int a, int b) {
        return service.calculateSum(a, b);
    }
}
