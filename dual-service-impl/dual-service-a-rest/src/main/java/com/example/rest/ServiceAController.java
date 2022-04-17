package com.example.rest;

import com.example.service.ServiceA;
import spark.Request;
import spark.Response;

public class ServiceAController {

    private final ServiceA serviceA;

    public ServiceAController(ServiceA serviceA) {
        this.serviceA = serviceA;
    }


    public Object sum(Request request, Response response) {
        return serviceA.calculateSum(Integer.parseInt(request.queryParams("a")), Integer.parseInt(request.queryParams("b")));
    }
}
