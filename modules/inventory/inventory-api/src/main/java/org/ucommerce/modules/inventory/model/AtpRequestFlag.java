package org.ucommerce.modules.inventory.model;

import java.util.Objects;

/**
 * A request flag, used to make custom ATP queries. Application defining their own AtpRequestFlags must ensure that the
 * id is unique.
 *
 * @param id
 * @param value
 */
public record AtpRequestFlag(String id, boolean value) {

    public AtpRequestFlag {
        Objects.requireNonNull(id, "id must be non-null");
    }

}
