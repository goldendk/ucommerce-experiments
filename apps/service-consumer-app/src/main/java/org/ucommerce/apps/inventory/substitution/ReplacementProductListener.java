package org.ucommerce.apps.inventory.substitution;

import org.ucommerce.modules.inventory.model.*;
import org.ucommerce.modules.inventory.services.AtpLookupListener;
import org.ucommerce.modules.inventory.services.AtpRequestFlagHelper;
import org.ucommerce.modules.inventory.services.AtpService;
import org.ucommerce.shared.kernel.factory.ObjectFactory;
import org.ucommerce.shared.kernel.ids.ProductId;

import java.util.Arrays;
import java.util.Objects;

public class ReplacementProductListener implements AtpLookupListener {


    //FIXME: put the parameters for this method into a single object instead.
    @Override
    public void accept(AtpService atpService,
                       AtpCalculation calculation,
                       ProductId productId,
                       LocationId locationId,
                       AtpRequestFlag[] flags) {
        if (!AtpRequestFlagHelper.contains(ReplacementProductRule.REPLACEMENT_FLAG_ID, flags)) {
            return;
        }

        ReplacementRepository repo = ObjectFactory.createFactory().createObject(ReplacementRepository.class);

        ReplacementChain chain = repo.getChain(productId);
        if (chain != null) {
            Arrays.stream(chain.products()).forEach(chainProduct -> {

                if (!Objects.equals(productId, chainProduct)) {
                    //Important otherwise an infinite loop is triggered.
                    AtpRequestFlag[] flagsWithoutReplacementRule = AtpRequestFlagHelper.disable(ReplacementProductRule.REPLACEMENT_FLAG_ID, flags);
                    AtpResult atpResult = atpService.getAtp(new AtpRequestData(chainProduct, locationId, flagsWithoutReplacementRule));
                    if (atpResult.atps()[0].amount().quantity() > 0) {
                        calculation.addAmount(atpResult.atps()[0].amount(), "replacement[" + chainProduct + "]");
                    }
                }
            });
        }
    }

}
