package org.ucommerce.modules.inventory.services;

import org.ucommerce.modules.inventory.model.LocationId;
import org.ucommerce.shared.kernel.ids.ProductId;

public record InventoryStock(LocationId locationId, ProductId productId, int stock) {
}
