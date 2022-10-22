package org.ucommerce.modules.orders.external;

import org.ucommerce.shared.kernel.ids.ItemId;
import org.ucommerce.shared.kernel.ids.OrderId;
import org.ucommerce.shared.kernel.ids.ProductId;
import org.ucommerce.shared.kernel.services.ExternalService;

import java.math.BigDecimal;

@ExternalService
public interface OrdersService {

    UOrderDTO getOrder(OrderId orderId);

    OrderId createOrder();

    void addProduct(OrderId orderId, ProductId productId, int quantity);

    void modifyItem(OrderId orderId, ItemId itemId, int quantity);

}
