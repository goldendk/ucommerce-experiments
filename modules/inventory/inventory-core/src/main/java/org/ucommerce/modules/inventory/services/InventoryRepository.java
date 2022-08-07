package org.ucommerce.modules.inventory.services;

import org.ucommerce.modules.inventory.model.LocationId;
import org.ucommerce.shared.kernel.ids.CacheId;
import org.ucommerce.shared.kernel.ids.ProductId;

public interface InventoryRepository {
    CacheId STOCK_CACHE_ID = CacheId.of("location-stock");

    Iterable<LocationId> getLocationsForProduct(CacheId cacheId, ProductId productId);

    Iterable<ProductId> getProductsForLocation(CacheId cacheId, LocationId locationId);


    InventoryStock getStock(CacheId cacheId, LocationId locationId, ProductId productId);

}
