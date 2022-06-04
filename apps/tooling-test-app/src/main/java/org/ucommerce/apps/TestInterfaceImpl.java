package org.ucommerce.apps;

public class TestInterfaceImpl implements TestInterface {
    @Override
    public String getValue(int input) {
        return String.valueOf(input);
    }

    @Override
    public String createThing(ThingData data) {
        return "abc";
    }
}
