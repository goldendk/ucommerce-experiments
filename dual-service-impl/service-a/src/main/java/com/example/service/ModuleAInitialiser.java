package com.example.service;

import com.example.shared.ModuleInitialiser;
import com.example.shared.factory.ObjectFactory;

public class ModuleAInitialiser implements ModuleInitialiser {
    @Override
    public void initialise() {
        ObjectFactory.createFactory().register(ServiceA.class, ServiceAImpl.class);
    }
}
