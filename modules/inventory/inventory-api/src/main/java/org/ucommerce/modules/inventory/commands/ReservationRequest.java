package org.ucommerce.modules.inventory.commands;

import org.ucommerce.modules.inventory.model.Atp;

public record ReservationRequest(Atp[] atp, String externalIdentifier) {
}
