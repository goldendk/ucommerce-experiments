package org.ucommerce.modules.inventory.services;

import org.ucommerce.modules.inventory.commands.ReservationRequest;
import org.ucommerce.modules.inventory.model.*;
import org.ucommerce.shared.kernel.factory.ObjectFactory;
import org.ucommerce.shared.kernel.ids.ProductId;

import java.util.ArrayList;
import java.util.List;

public class InventoryCoreService implements AtpService {
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
