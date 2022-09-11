package org.ucommerce.modules.inventory.services;

import org.ucommerce.modules.inventory.model.Amount;
import org.ucommerce.modules.inventory.model.LocationId;
import org.ucommerce.shared.kernel.ids.CacheId;
import org.ucommerce.shared.kernel.ids.ProductId;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class MockInventoryRepository implements InventoryRepository {
    private Map<CacheId, Map<String, InventoryStock>> inventoryStocks = new HashMap<>();

    private Map<CacheId, Map<ProductId, Collection<LocationId>>> productToLocation = new HashMap<>();
    private Map<CacheId, Map<LocationId, Collection<ProductId>>> locationToProduct = new HashMap<>();


    public void addStock(CacheId cacheId, LocationId locationId, ProductId productId, long stock) {
        checkCacheExist(cacheId);

        String key = toKey(locationId, productId);

        inventoryStocks.get(cacheId).put(key, new InventoryStock(locationId, productId, stock));
        productToLocation.get(cacheId).computeIfAbsent(productId, e -> new HashSet<>());
        locationToProduct.get(cacheId).computeIfAbsent(locationId, e -> new HashSet<>());
        productToLocation.get(cacheId).get(productId).add(locationId);
        locationToProduct.get(cacheId).get(locationId).add(productId);

    }

    private void checkCacheExist(CacheId cacheId) {
        if (!inventoryStocks.containsKey(cacheId)) {
            inventoryStocks.put(cacheId, new HashMap<>());
        }
        if (!productToLocation.containsKey(cacheId)) {
            productToLocation.put(cacheId, new HashMap<>());
        }
        if (!locationToProduct.containsKey(cacheId)) {
            locationToProduct.put(cacheId, new HashMap<>());
        }
    }

    private String toKey(LocationId locationId, ProductId productId) {
        return locationId + "." + productId;
    }

    @Override
    public Iterable<LocationId> getLocationsForProduct(CacheId cacheId, ProductId productId) {
        if (!productToLocation.containsKey(cacheId)) {
            return null;
        }
        return productToLocation.get(cacheId).get(productId);
    }

    @Override
    public Iterable<ProductId> getProductsForLocation(CacheId cacheId, LocationId locationId) {
        if (!locationToProduct.containsKey(cacheId)) {
            return null;
        }
        return locationToProduct.get(cacheId).get(locationId);
    }

    @Override
    public InventoryStock getStock(CacheId cacheId, LocationId locationId, ProductId productId) {
        if (!inventoryStocks.containsKey(cacheId)) {
            return null;
        }
        return inventoryStocks.get(cacheId).get(toKey(locationId, productId));
    }

    @Override
    public void setStock(CacheId cacheId, LocationId location, ProductId productId, Amount amount) {
        addStock(cacheId, location, productId, amount.quantity());
    }
}
