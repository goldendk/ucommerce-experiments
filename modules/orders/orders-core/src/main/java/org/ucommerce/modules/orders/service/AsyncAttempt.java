package org.ucommerce.modules.orders.service;

import org.ucommerce.shared.kernel.UAudit;

import java.util.List;

public record AsyncAttempt(UAudit audit, String outcome, List<String> log) {




}
