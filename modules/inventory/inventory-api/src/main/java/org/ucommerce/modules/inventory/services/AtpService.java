package org.ucommerce.modules.inventory.services;

import org.ucommerce.modules.inventory.commands.ReservationRequest;
import org.ucommerce.modules.inventory.model.AtpRequestData;
import org.ucommerce.modules.inventory.model.AtpResult;
import org.ucommerce.shared.kernel.services.ExternalService;

@ExternalService
public interface AtpService {

    /**
     * Returns the ATP for all products and locations provided. Use '*' if all locations or products should be returned.
     *
     * @param productRequest
     * @return
     */
    AtpResult getAtp(AtpRequestData productRequest);

    /**
     * Moves the given inventory to a reserved column.
     *
     * @param reservationRequest
     */
    void createReservation(ReservationRequest reservationRequest);


    /**
     * Clears any reservations made for the external identifier and allocates the reserved amount to available stock.
     *
     * @param externalIdentifier
     */
    void clearReservation(String externalIdentifier);

}
