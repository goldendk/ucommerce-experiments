package org.ucommerce.modules.inventory.commands;

import org.ucommerce.modules.inventory.model.Inventory;

public record ReservationRequest(Inventory[] inventory, String externalIdentifier) {
}
