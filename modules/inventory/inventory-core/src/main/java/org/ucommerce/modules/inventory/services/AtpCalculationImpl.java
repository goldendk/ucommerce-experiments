package org.ucommerce.modules.inventory.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ucommerce.modules.inventory.model.Amount;
import org.ucommerce.modules.inventory.model.AtpCalculation;
import org.ucommerce.modules.inventory.model.AtpDelta;
import org.ucommerce.modules.inventory.model.AtpOperation;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AtpCalculationImpl implements AtpCalculation {
    private static Logger logger = LoggerFactory.getLogger(AtpCalculationImpl.class);

    private Amount amount;

    private List<AtpDelta> deltas = new ArrayList<>();

    @Override
    public Amount getCurrentAmount() {
        return amount;
    }

    @Override
    public void addAmount(Amount toAdd, String source) {
        if (this.amount == null) {
            this.setAmount(toAdd, source);
        }
        if (!Objects.equals(this.amount.unit(), toAdd.unit())) {
            throw new IllegalArgumentException(
                    MessageFormat.format("Cannot add different units. Current: {0}, attempt to add: {1}",
                            this.amount.unit(), toAdd.unit()));
        }
        logger.trace("Adding amount {} from source {}", toAdd, source);
        this.amount = new Amount(toAdd.quantity() + amount.quantity(), toAdd.unit());
        this.deltas.add(new AtpDelta(toAdd.quantity(), source, AtpOperation.ADD));
    }

    @Override
    public void setAmount(Amount toSet, String source) {

        if (!deltas.isEmpty()) {
            deltas.clear();
        }

        deltas.add(new AtpDelta(toSet.quantity(), source, AtpOperation.SET));
        this.amount = toSet;
    }
}
