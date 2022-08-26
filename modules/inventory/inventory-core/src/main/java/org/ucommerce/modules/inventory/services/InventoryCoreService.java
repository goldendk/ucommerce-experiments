package org.ucommerce.modules.inventory.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ucommerce.modules.inventory.commands.ReservationRequest;
import org.ucommerce.modules.inventory.model.*;
import org.ucommerce.shared.kernel.UCConstants;
import org.ucommerce.shared.kernel.factory.ObjectFactory;
import org.ucommerce.shared.kernel.ids.ProductId;

import java.util.ArrayList;
import java.util.List;

public class InventoryCoreService implements AtpService {
    private static Logger logger = LoggerFactory.getLogger(InventoryCoreService.class);
    private final InventoryRepository inventoryRepository;

    public InventoryCoreService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public AtpResult getAtp(AtpRequestData requestData) {

        Iterable<AtpModifier> modifiers = loadAtpModifiers();

        List<Atp> atpResult = new ArrayList<>();
        for (ProductId productId : requestData.productIds()) {
            for (LocationId locationId : requestData.locationIds()) {
                AtpCalculation calculation = new AtpCalculationImpl();

                for (AtpModifier modifier : modifiers) {
                    modifier.modifyAtp(calculation, productId, locationId);
                }

                Iterable<AtpLookupListener> Listeners = InventoryCore.getAtpCore().getAtpLookupListeners();
                Listeners.forEach(e -> {
                    //FIXME: This gives stack overflow. We should instead register listeners with unique keys so that an atp request can
                    // be made in such a way that the same listener is never invoked when calling the ATP service recursively.
                    e.accept(this, calculation, productId, locationId, requestData.flags());
                });

                if (calculation.getCurrentAmount() == null) {
                    logger.trace("Setting amount to <0> for product {} at location {}", productId, locationId);
                    calculation.addAmount(new Amount(0L, UCConstants.INVENTORY_UNIT_PCS), "default-value");
                }

                atpResult.add(new Atp(calculation.getCurrentAmount(), locationId, productId));
            }
        }


        return new AtpResult(atpResult.toArray(new Atp[0]));
    }

    private Iterable<AtpModifier> loadAtpModifiers() {

        return ObjectFactory.createFactory().createObject(AtpModifierListProvider.class).getCacheNames();

    }

    private Object stockToAtp(InventoryStock e) {
        return new Atp(new Amount(e.stock(), "pcs"), e.locationId(), e.productId());
    }

    @Override
    public void createReservation(ReservationRequest reservationRequest) {

    }

    @Override
    public void clearReservation(String externalIdentifier) {

    }
}
