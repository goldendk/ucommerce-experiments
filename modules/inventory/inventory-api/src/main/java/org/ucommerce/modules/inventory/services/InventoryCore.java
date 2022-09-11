package org.ucommerce.modules.inventory.services;

import org.ucommerce.shared.kernel.factory.ObjectFactory;

/**
 * Core class used to register listeners, validators etc.
 */
public class InventoryCore {
    //FIXME: This should be multi tenant compliant.
    private static final AtpCore ATP_CORE = new AtpCore();

    public static AtpCore getAtpCore() {
        return ATP_CORE;
    }

    public static void clear() {
        ATP_CORE.clearAtpLookupListeners();
    }


}
