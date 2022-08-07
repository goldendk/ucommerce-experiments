package org.ucommerce.apps.inventory;

import org.ucommerce.modules.inventory.model.AtpCalculation;
import org.ucommerce.modules.inventory.services.AtpLookupListener;
import org.ucommerce.modules.inventory.services.AtpService;
import org.ucommerce.modules.inventory.services.InventoryCore;

public class SubstitutionProductRule {

    public void registerRule() {
        InventoryCore.getAtpCore().registerAtpLookupListener((service, result)->new ReplacementProductListener().accept(service, result));

    }


    public static class ReplacementProductListener implements AtpLookupListener {

        @Override
        public void accept(AtpService atpService, AtpCalculation calculation) {


        }
    }
}
