package com.freddy.furniture.app;

import org.ucommerce.modules.inventory.model.Amount;
import org.ucommerce.modules.inventory.model.Atp;
import org.ucommerce.modules.inventory.model.LocationId;
import org.ucommerce.modules.inventory.services.InboundService;
import org.ucommerce.shared.kernel.UCConstants;
import org.ucommerce.shared.kernel.factory.ObjectFactory;
import org.ucommerce.shared.kernel.ids.ProductId;

/**
 * Example class of how the just in the inventory module is adjusted.
 */
//FIXME: Move this to a plugin module so it can be reused.
public class StockAdjuster {

    public void setInitialStock() {
        InboundService inboundService = ObjectFactory.createFactory().createObject(InboundService.class);
        for (ProductId productId : FreddyProducts.allProducts()) {
            for (LocationId locationId : FreddyStores.allStores()) {
                Amount amount = new Amount(100, UCConstants.INVENTORY_UNIT_PCS);
                inboundService.setInventory(UCConstants.STOCK_CACHE_ID, new Atp(amount, locationId, productId));
            }
        }
    }


    public void adjustStock() {

        InboundService inboundService = ObjectFactory.createFactory().createObject(InboundService.class);

        inboundService.setInventory(UCConstants.STOCK_CACHE_ID,
                new Atp(new Amount(1, UCConstants.INVENTORY_UNIT_PCS),
                        FreddyStores.allStores()[0],
                        FreddyProducts.allProducts()[0])
        );

    }


}
