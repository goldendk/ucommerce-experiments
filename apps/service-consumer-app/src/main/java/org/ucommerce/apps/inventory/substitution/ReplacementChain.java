package org.ucommerce.apps.inventory.substitution;

import org.ucommerce.shared.kernel.ids.ProductId;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A substitution chain.
 *
 * @param activeProduct the currently active product in the chain.
 * @param products      the products in the chain. Must also contain the active product id.
 */
public record ReplacementChain(ProductId activeProduct, ProductId[] products) {
    public ReplacementChain {
        Objects.requireNonNull(activeProduct, "Active product cannot be null");
        Objects.requireNonNull(products, "Products array can not be null (but can be empty)");
    }
}
