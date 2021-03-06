package com.ucommerce.testapp;

import org.ucommerce.shared.kernel.services.ExternalService;

@ExternalService
public interface FooService {

    void someRandomCommand(); //expect GET REST here.

    BarRecord someOtherCommand(); //expect GET REST here.

    BarRecord getBar(String name); // GET request. Should set name as query parameter.

    BarRecord getBar(BarQuery query); // POST request due to complex type. Should set name as query parameter.

}
