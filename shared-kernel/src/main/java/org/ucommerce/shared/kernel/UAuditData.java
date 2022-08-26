package org.ucommerce.shared.kernel;

import java.time.Instant;

public record UAuditData(String user, Instant dateTime) {
}
