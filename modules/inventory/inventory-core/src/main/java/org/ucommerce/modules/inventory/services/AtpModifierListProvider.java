package org.ucommerce.modules.inventory.services;

import org.ucommerce.shared.kernel.ids.CacheId;

import java.util.List;

public class AtpModifierListProvider {

    public Iterable<AtpModifier> getCacheNames(){
        //FIXME: move this to contants class instead of magic variables.


        return List.of(
                new SetStockAtpModifier(InventoryRepository.STOCK_CACHE_ID )
                );
    }
}
