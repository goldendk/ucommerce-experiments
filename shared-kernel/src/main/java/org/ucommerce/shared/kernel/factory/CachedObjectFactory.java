package org.ucommerce.shared.kernel.factory;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CachedObjectFactory extends DefaultObjectFactory {
    private final static Map<Class, Object> OBJECT_CACHE = Collections.synchronizedMap(new HashMap<>());

    @Override
    public <T> T createObject(Class<T> clazz) {
        if (OBJECT_CACHE.containsKey(clazz)) {
            return (T) OBJECT_CACHE.get(clazz);
        } else {
            T object = super.createObject(clazz);
            OBJECT_CACHE.put(clazz, object);
            return object;
        }
    }

    @Override
    public <C> void registerInstance(Class<? super C> superClass, C instance) {
        super.registerInstance(superClass, instance);
        OBJECT_CACHE.put(superClass, instance);
    }
}
