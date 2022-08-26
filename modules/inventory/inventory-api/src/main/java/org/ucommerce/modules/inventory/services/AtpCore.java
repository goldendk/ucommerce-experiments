package org.ucommerce.modules.inventory.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Core class for the Atp domain.
 */
public class AtpCore {
    private static final Collection<AtpLookupListener> ATP_LOOKUP_LISTENERS = new ArrayList();
    public void registerAtpLookupListener(AtpLookupListener listener) {
        ATP_LOOKUP_LISTENERS.add(listener);
    }

    public void clearAtpLookupListeners() {
        ATP_LOOKUP_LISTENERS.clear();
    }

    public Iterable<AtpLookupListener> getAtpLookupListeners() {
        return ATP_LOOKUP_LISTENERS;
    }
}
