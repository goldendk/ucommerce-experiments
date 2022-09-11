package org.ucommerce.modules.inventory.services;

import org.ucommerce.modules.inventory.model.Atp;
import org.ucommerce.shared.kernel.ids.CacheId;
import org.ucommerce.shared.kernel.ids.ProductId;

/**
 *
 */
public interface InboundService {

    /**
     * Modifies the inventory with the specified amount.
     * @param inventory
     * @param productId
     */
    void modifyInventory(CacheId cacheId, Atp inventory, ProductId productId);

    /**
     * Sets the inventory value to the exact value provided.
     * @param atp
     */
    void setInventory(CacheId cacheId, Atp atp);

}
