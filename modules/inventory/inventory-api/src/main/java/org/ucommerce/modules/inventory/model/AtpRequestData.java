package org.ucommerce.modules.inventory.model;

import org.ucommerce.modules.inventory.model.LocationId;
import org.ucommerce.shared.kernel.ids.ProductId;

public record AtpRequestData(ProductId[] productIds, LocationId[] locationIds) {
}
