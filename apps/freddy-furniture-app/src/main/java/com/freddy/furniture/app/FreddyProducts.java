package com.freddy.furniture.app;

import org.ucommerce.shared.kernel.ids.ProductId;

public class FreddyProducts {

    public static ProductId[] allProducts() {
        return new ProductId[]{
          createId("product1"),
          createId("product2"),
          createId("product3"),
          createId("product4"),
          createId("product5"),
          createId("product6"),
          createId("product7"),
          createId("product8"),
          createId("product9"),
          createId("product10")
        };
    }

    public static ProductId createId(String id) {
        return new ProductId(id);
    }
}
