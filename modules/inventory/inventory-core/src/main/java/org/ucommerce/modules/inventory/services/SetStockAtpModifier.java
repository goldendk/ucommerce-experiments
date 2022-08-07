package org.ucommerce.modules.inventory.services;

import org.ucommerce.modules.inventory.model.Amount;
import org.ucommerce.modules.inventory.model.AtpCalculation;
import org.ucommerce.shared.kernel.UCConstants;
import org.ucommerce.shared.kernel.ids.CacheId;

import java.util.function.BiConsumer;

/**
 *
 */
public class SetStockAtpModifier extends NamedCacheModifier {

    public SetStockAtpModifier(CacheId cacheId) {
        super(cacheId, (calc, stock) -> calc.setAmount(new Amount(stock.stock(), UCConstants.INVENTORY_UNIT_PCS), UCConstants.STOCK_CACHE_NAME));
    }

}
