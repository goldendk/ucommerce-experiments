package org.ucommerce.modules.inventory.services;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Core class for the Atp domain.
 */
public class AtpCore {
    private static final Collection<AtpLookupListener> ATP_LOOKUP_LISTENERS = new ArrayList();
    public void registerAtpLookupListener(AtpLookupListener listener) {
        ATP_LOOKUP_LISTENERS.add(listener);
    }
}
