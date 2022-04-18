package org.ucommerce.modules.inventory.model;

import org.ucommerce.shared.kernel.ids.ProductId;

public record ProductRequest(ProductId productId, LocationId[] locationIds) {
}
