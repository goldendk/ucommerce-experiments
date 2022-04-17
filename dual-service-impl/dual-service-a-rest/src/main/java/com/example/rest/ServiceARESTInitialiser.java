package com.example.rest;

import com.example.shared.ModuleInitialiser;
import com.example.shared.factory.ObjectFactory;
import spark.Spark;

public class ServiceARESTInitialiser implements ModuleInitialiser {
    @Override
    public void initialise() {

        ObjectFactory.createFactory().register(ServiceAController.class, ServiceAController.class);
        Spark.get("/hello", createAController()::sum);
    }

    private ServiceAController createAController() {
        return ObjectFactory.createFactory().createObject(ServiceAController.class);
    }
}
