package org.ucommerce.modules.inventory.services;

import org.ucommerce.modules.inventory.model.Amount;
import org.ucommerce.shared.kernel.UCConstants;
import org.ucommerce.shared.kernel.ids.CacheId;

/**
 * Modifier that adds the stock from the named cache to the result of the ATP calculation.
 */
public class AddStockAtpModifier extends NamedCacheModifier {
    public AddStockAtpModifier(CacheId cacheId) {
        super(cacheId, (calc, stock) -> calc.addAmount(new Amount(stock.stock(), UCConstants.INVENTORY_UNIT_PCS), cacheId.getValue()));
    }


}
