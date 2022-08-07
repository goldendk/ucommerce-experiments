package org.ucommerce.modules.inventory.services;

import org.ucommerce.modules.inventory.model.AtpCalculation;

public interface AtpLookupListener {

    void accept(AtpService atpService, AtpCalculation atpCalculation);
}
