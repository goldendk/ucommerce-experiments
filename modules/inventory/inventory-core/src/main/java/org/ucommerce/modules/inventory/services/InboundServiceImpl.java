package org.ucommerce.modules.inventory.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ucommerce.modules.inventory.model.Amount;
import org.ucommerce.modules.inventory.model.Atp;
import org.ucommerce.shared.kernel.ids.CacheId;
import org.ucommerce.shared.kernel.ids.ProductId;

public class InboundServiceImpl implements InboundService {

    private static Logger logger = LoggerFactory.getLogger(InboundServiceImpl.class);

    public final InventoryRepository inventoryRepository;

    public InboundServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }


    @Override
    public void modifyInventory(CacheId cacheId, Atp atp, ProductId productId) {

        InventoryStock stock = inventoryRepository.getStock(cacheId, atp.location(), atp.productId());

        if (stock == null) {
            setInventory(cacheId, atp);
        } else {
            Amount newAmount = new Amount(stock.stock() + atp.amount().quantity(), atp.amount().unit());

            logger.info("Modifying inventory to {} for location {} and product {} in cache {}",
                    new Object[]{newAmount, atp.location(), atp.productId(), cacheId});

            inventoryRepository.setStock(cacheId, atp.location(), atp.productId(), newAmount);
        }

    }

    @Override
    public void setInventory(CacheId cacheId, Atp atp) {
        logger.info("Setting inventory to {} in cache {}", atp, cacheId);
        inventoryRepository.setStock(cacheId, atp.location(), atp.productId(), atp.amount());
    }
}
