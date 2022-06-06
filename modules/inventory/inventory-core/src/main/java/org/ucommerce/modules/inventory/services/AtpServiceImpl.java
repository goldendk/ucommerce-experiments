package org.ucommerce.modules.inventory.services;

import org.ucommerce.modules.inventory.commands.ReservationRequest;
import org.ucommerce.modules.inventory.model.Inventory;
import org.ucommerce.modules.inventory.model.LocationRequest;
import org.ucommerce.modules.inventory.model.ProductRequest;

public class AtpServiceImpl implements AtpService{
    @Override
    public Inventory getAtp(ProductRequest productRequest) {
        return null;
    }

    @Override
    public Inventory getAtp(LocationRequest productRequest) {
        return null;
    }

    @Override
    public void createReservation(ReservationRequest reservationRequest) {

    }

    @Override
    public void clearReservation(String externalIdentifier) {

    }
}
