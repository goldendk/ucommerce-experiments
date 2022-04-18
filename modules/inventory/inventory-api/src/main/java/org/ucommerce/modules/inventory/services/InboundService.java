package org.ucommerce.modules.inventory.services;

import org.ucommerce.modules.inventory.model.Inventory;
import org.ucommerce.shared.kernel.ids.ProductId;

public interface InboundService {

    void modifyInventory(Inventory inventory, ProductId productId);
    void setInventory(Inventory inventory, ProductId productId);

}
