package org.ucommerce.modules.inventory.services;

import org.ucommerce.modules.inventory.model.AtpCalculation;
import org.ucommerce.modules.inventory.model.AtpRequestFlag;
import org.ucommerce.modules.inventory.model.LocationId;
import org.ucommerce.shared.kernel.ids.ProductId;

public interface AtpLookupListener {

    void accept(AtpService atpService, AtpCalculation atpCalculation, ProductId productId, LocationId locationId, AtpRequestFlag[] flags);
}
