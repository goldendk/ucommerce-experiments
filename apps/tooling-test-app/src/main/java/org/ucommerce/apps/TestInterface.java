package org.ucommerce.apps;

import org.ucommerce.shared.kernel.services.ExternalService;

@ExternalService
public interface TestInterface {
    String getValue(int input);

    String createThing(ThingData data);
}
