package org.ucommerce.modules.orders.api.internal;

/**
 * Core class for the 'orders' module. Allows plugin modules to register validator, listeners etc.
 */
public class OrdersCore {
    public static final String DEFAULT_ORDER_TYPE = "default";

    public void registerOrderType(String orderType){

    }

    public void registerItemGroupType(String orderType, String itemGroupType){

    }

    public void registerOrderValidator(String orderType, OrderValidator orderValidator){

    }

    public void registerItemGroupValidator(String orderType, String itemGroupType, ItemGroupValidator itemGroupValidator){

    }

    public void registerPaymentStrategy(String orderType, String itemGroupType, PaymentStrategy paymentStrategy){

    }
    public void registerDeliveryStrategy(String orderType, String itemGroupType, DeliveryStrategy deliveryStrategy){

    }

}
