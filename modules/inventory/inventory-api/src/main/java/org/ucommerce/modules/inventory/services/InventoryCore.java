package org.ucommerce.modules.inventory.services;

/**
 * Core class used to register listeners, validators etc.
 */
public class InventoryCore {

    private static final AtpCore ATP_CORE = new AtpCore();

    public static AtpCore getAtpCore() {
        return ATP_CORE;
    }

    public static void clear() {
        ATP_CORE.clearAtpLookupListeners();
    }
}
