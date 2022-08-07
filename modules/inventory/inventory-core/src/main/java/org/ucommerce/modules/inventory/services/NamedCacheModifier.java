package org.ucommerce.modules.inventory.services;

import org.ucommerce.modules.inventory.model.AtpCalculation;
import org.ucommerce.modules.inventory.model.LocationId;
import org.ucommerce.shared.kernel.factory.ObjectFactory;
import org.ucommerce.shared.kernel.ids.CacheId;
import org.ucommerce.shared.kernel.ids.ProductId;

import java.util.function.BiConsumer;

public abstract class NamedCacheModifier implements AtpModifier {

    protected final InventoryRepository repository;
    protected final CacheId cacheId;
    private final BiConsumer<AtpCalculation, InventoryStock> atpModifierFunction;

    public NamedCacheModifier(CacheId cacheId, BiConsumer<AtpCalculation, InventoryStock> atpModifierFunction) {
        this.cacheId = cacheId;

        repository = ObjectFactory.createFactory().createObject(InventoryRepository.class);
        this.atpModifierFunction = atpModifierFunction;
    }

    @Override
    public void modifyAtp(AtpCalculation calculation, ProductId productId, LocationId locationId) {
        InventoryStock stock = repository.getStock(cacheId, locationId, productId);
        if (stock != null) {
            atpModifierFunction.accept(calculation, stock);
        }
    }
}
