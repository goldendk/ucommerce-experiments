package com.ucommerce.testapp;

import org.ucommerce.shared.kernel.services.ExternalService;

/**
 * To test generating crud methods in http.
 */
@ExternalService
public interface CrudBarService {

    BarRecord getBar(String name); //GET request here due to simple type.

    BarRecord getBar(BarQuery query); // POST request due to complex type. Should set name as query parameter.

    void deleteBar(String name); // DELETE request expected.

    String createBar(BarRecord record); // POST expected

    void updateBar(BarRecord record); // PUT expected

}
