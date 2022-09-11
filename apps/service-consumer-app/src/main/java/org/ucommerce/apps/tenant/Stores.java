package org.ucommerce.apps.tenant;

import org.ucommerce.modules.inventory.model.LocationId;

public class Stores {
    public static LocationId[] allStores(){
        return new LocationId[]{
                createId("store1"),
                createId("store2"),
                createId("store3"),
                createId("store4"),
                createId("store5")
        };
    }
    public static LocationId createId(String locationId) {
        return new LocationId("store", locationId);
    }
}
