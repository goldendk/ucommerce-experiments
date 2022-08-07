package org.ucommerce.modules.inventory.model;

import org.ucommerce.shared.kernel.ids.ProductId;

/**
 * Models a specific amount of inventory at a specific location.
 */
public record Atp(Amount amount, LocationId location, ProductId productId) {};
