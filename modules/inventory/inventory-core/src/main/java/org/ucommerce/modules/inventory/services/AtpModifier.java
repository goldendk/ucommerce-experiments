package org.ucommerce.modules.inventory.services;

import org.ucommerce.modules.inventory.model.AtpCalculation;
import org.ucommerce.modules.inventory.model.LocationId;
import org.ucommerce.shared.kernel.ids.ProductId;

public interface AtpModifier {

    void modifyAtp(AtpCalculation calculation, ProductId productId, LocationId locationId);

}
