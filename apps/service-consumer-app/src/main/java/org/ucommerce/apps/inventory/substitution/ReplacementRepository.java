package org.ucommerce.apps.inventory.substitution;

import org.ucommerce.shared.kernel.ids.ProductId;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Repository class for product substitution data. It is assumed that any product can only be part of one chain.
 */
public class ReplacementRepository {

    private static Map<ProductId, ReplacementChain> chains = new HashMap<>();

    public void addChain(ReplacementChain chain) {
        chains.put(chain.activeProduct(), chain);
        Arrays.stream(chain.products()).forEach(e -> chains.put(e, chain));
    }

    public void removeChain(ReplacementChain chain) {
        chains.remove(chain.activeProduct());
        Arrays.stream(chain.products()).forEach(e -> chains.remove(e));
    }

    public ReplacementChain getChain(ProductId productId) {
        return chains.get(productId);
    }
}
