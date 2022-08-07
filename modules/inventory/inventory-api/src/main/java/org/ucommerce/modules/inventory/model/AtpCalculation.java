package org.ucommerce.modules.inventory.model;

public interface AtpCalculation {

    Amount getCurrentAmount();

    void addAmount(Amount amount, String source);

    void setAmount(Amount amount, String source);

}
