package com.example.rest;

import com.example.shared.ModuleInitialiser;
import com.example.shared.configuration.ModuleConfig;
import com.example.shared.configuration.PropertyName;
import com.example.shared.factory.ObjectFactory;
import spark.Spark;

import java.util.List;

public class ServiceARESTInitialiser implements ModuleInitialiser {
    private static PropertyName REST_PORT = PropertyName.of("rest.port");

    @Override
    public void initialise() {


        ObjectFactory.createFactory().register(ServiceAController.class, ServiceAController.class);
        List<Integer> integerList = ModuleConfig.loadConfiguration().getIntegerList(REST_PORT);
        Spark.port(integerList.get(0));
        Spark.get("/hello", createAController()::sum);
    }

    private ServiceAController createAController() {
        return ObjectFactory.createFactory().createObject(ServiceAController.class);
    }
}
