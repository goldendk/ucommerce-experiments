package org.ucommerce.modules.inventory.model;

import org.ucommerce.modules.inventory.model.LocationId;
import org.ucommerce.shared.kernel.ids.ProductId;

public record AtpRequestData(ProductId[] productIds, LocationId[] locationIds, AtpRequestFlag[] flags) {

    public AtpRequestData(ProductId[] productIds, LocationId[] locationIds) {
        this(productIds, locationIds, new AtpRequestFlag[0]);
    }

    public AtpRequestData(ProductId productId, LocationId locationId) {
        this(new ProductId[]{productId}, new LocationId[]{locationId}, new AtpRequestFlag[0]);
    }

    public AtpRequestData(ProductId productId, LocationId locationId, AtpRequestFlag[] flags) {
        this(new ProductId[]{productId}, new LocationId[]{locationId}, flags);
    }

}
