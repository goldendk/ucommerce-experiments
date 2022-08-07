package org.ucommerce.modules.inventory.services;

import org.ucommerce.modules.inventory.model.Atp;
import org.ucommerce.shared.kernel.ids.ProductId;

public interface InboundService {

    void modifyInventory(Atp inventory, ProductId productId);
    void setInventory(Atp atp, ProductId productId);

}
