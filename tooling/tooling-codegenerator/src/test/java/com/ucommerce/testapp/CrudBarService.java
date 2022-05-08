package com.ucommerce.testapp;

/**
 * To test generating crud methods in http.
 */
public interface CrudBarService {

    BarRecord getBar(String name); //GET request here due to simple type.

    BarRecord getBar(BarQuery query); // POST request due to complex type. Should set name as query parameter.

    void deleteBar(String name); // DELETE request expected.

    String createBar(BarRecord record); // POST expected

    void updateBar(BarRecord record); // PUT expected

}
