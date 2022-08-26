package org.ucommerce.apps.inventory.substitution;

import org.ucommerce.modules.inventory.services.InventoryCore;

public class ReplacementProductRule {

    public static final String REPLACEMENT_FLAG_ID = "replacement-product";

    //FIXME : this should be part of a frame work initialization rather than a bespoke method.
    public void registerRule() {
        InventoryCore.getAtpCore().registerAtpLookupListener(
                (service, result, product, location, flags) -> new ReplacementProductListener().accept(service, result, product, location, flags));
    }


}
