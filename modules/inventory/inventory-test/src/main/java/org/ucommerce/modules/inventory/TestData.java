package org.ucommerce.modules.inventory;

import org.ucommerce.modules.inventory.model.LocationId;
import org.ucommerce.shared.kernel.ids.ProductId;

public class TestData {
    public static final String LOCATION_TYPE_SITE = "Site";
    public static final LocationId site1 = new LocationId(LOCATION_TYPE_SITE, "1" );
    public static final LocationId site2 = new LocationId(LOCATION_TYPE_SITE, "2" );


    public static final ProductId product1 = new ProductId("1234");
    public static final ProductId product2 = new ProductId("5678");


}
