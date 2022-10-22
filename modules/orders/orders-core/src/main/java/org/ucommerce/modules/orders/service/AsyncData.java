package org.ucommerce.modules.orders.service;

import org.ucommerce.shared.kernel.UAudit;

public abstract class AsyncData {
    private UAudit audit;
    /**
     * Type of deserializer to use.
     */
    private String type;
    /**
     * Version of deserializer to use.
     */
    private int version;

    public UAudit getAudit() {
        return audit;
    }

    public void setAudit(UAudit audit) {
        this.audit = audit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
