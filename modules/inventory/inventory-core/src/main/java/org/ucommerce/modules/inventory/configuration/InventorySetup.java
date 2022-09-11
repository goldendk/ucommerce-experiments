package org.ucommerce.modules.inventory.configuration;

import org.ucommerce.modules.inventory.services.*;
import org.ucommerce.shared.kernel.factory.ObjectFactory;

public class InventorySetup {
    /**
     * Initializes the inventory module with default configuration such as implementation classes for service interfaces.
     */
    public static void initializeWithDefaults() {
        ObjectFactory.createFactory().register(AtpModifierListProvider.class, AtpModifierListProvider.class);
        ObjectFactory.createFactory().register(InventoryRepository.class, MockInventoryRepository.class);
        ObjectFactory.createFactory().register(InboundService.class, InboundServiceImpl.class);
    }
}
