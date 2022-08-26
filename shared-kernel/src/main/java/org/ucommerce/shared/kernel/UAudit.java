package org.ucommerce.shared.kernel;

import java.util.Objects;

/**
 * Value holder for audit information.
 * @param created the created event.
 * @param modified the modified event.
 */
public record UAudit(UAuditData created, UAudit modified) {

    public UAudit {
        Objects.requireNonNull(created, "Created cannot be null.");
    }
}
