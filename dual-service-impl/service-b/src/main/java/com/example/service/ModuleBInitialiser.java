package com.example.service;

import com.example.shared.factory.ObjectFactory;

public class ModuleBInitialiser implements com.example.shared.ModuleInitialiser {
    @Override
    public void initialise() {
        ObjectFactory.createFactory().register(ServiceB.class, ServiceBImpl.class);
    }
}
