package org.ucommerce.modules.inventory;

import org.ucommerce.modules.inventory.services.AtpModifierListProvider;
import org.ucommerce.modules.inventory.services.InventoryRepository;
import org.ucommerce.shared.kernel.factory.ObjectFactory;

import java.util.Map;

public class InventoryTestFactory {
    /**
     * Initialize the inventory module in the object factory.
     *
     * @param overrides overrides to the standard test setup. Applied after default setup. Can be null.
     */
    public static void initializeObjectFactory(Map<Class, Class> overrides) {

        ObjectFactory.createFactory().register(AtpModifierListProvider.class, AtpModifierListProvider.class);
        ObjectFactory.createFactory().register(InventoryRepository.class, MockInventoryRepository.class);

        if (overrides != null) {
            for (Map.Entry<Class, Class> entry : overrides.entrySet()) {
                ObjectFactory.createFactory().register(entry.getKey(), entry.getValue());
            }
        }

    }
}
