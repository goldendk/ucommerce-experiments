package org.ucommerce.modules.inventory.services;

import org.ucommerce.modules.inventory.model.Amount;
import org.ucommerce.modules.inventory.model.AtpCalculation;

import java.text.MessageFormat;
import java.util.Objects;

public class AtpCalculationImpl implements AtpCalculation {
    private Amount amount;


    @Override
    public Amount getCurrentAmount() {
        return amount;
    }

    @Override
    public void addAmount(Amount toAdd, String source) {
        if (this.amount == null) {
            throw new IllegalStateException("Amount not set yet, use setAmount(...).");
        }
        if (Objects.equals(this.amount.unit(), toAdd.unit())) {
            throw new IllegalArgumentException(
                    MessageFormat.format("Cannot add different units. Current: {0}, attempt to add: {1}",
                            this.amount.unit(), toAdd.unit()));
        }

        this.amount = new Amount(toAdd.quantity(), toAdd.unit());
    }

    @Override
    public void setAmount(Amount toSet, String source) {
        this.amount = toSet;
    }
}
