package org.ucommerce.modules.inventory.model;

/**
 * Models a location in the inventory domain.
 */
public record LocationId(String type, String value) {
    public String getId() {
        return this.type + "_" + this.value;
    }
}