package org.ucommerce.modules.inventory.services;

import org.ucommerce.modules.inventory.model.Inventory;
import org.ucommerce.modules.inventory.model.LocationRequest;
import org.ucommerce.modules.inventory.model.ProductRequest;
import org.ucommerce.shared.kernel.services.ExternalService;

@ExternalService
public interface AtpService {

    /**
     * Returns the ATP in all locations requested by product.
     *
     * @param productRequest
     * @return
     */
    Inventory getAtp(ProductRequest productRequest);

    /**
     * Returns the ATP for all products requested by location.
     *
     * @param productRequest
     * @return
     */
    Inventory getAtp(LocationRequest productRequest);

    /**
     * Moves the given inventory to a reserved column.
     *
     * @param inventories        a list of inventories to reserve.
     * @param externalIdentifier an identifier from e.g. an order system.
     */
    void createReservation(Inventory[] inventories, String externalIdentifier);


    /**
     * Clears any reservations made for the external identifier and allocates the reserved amount to available stock.
     *
     * @param externalIdentifier
     */
    void clearReservation(String externalIdentifier);

}
