package org.ucommerce.shared.kernel.factory;

import java.util.Map;

public abstract class ObjectFactory {

    public static ObjectFactory createFactory() {
        return new CachedObjectFactory();
    }


    public abstract <T> T createObject(Class<T> clazz);

    public abstract <C> void register(Class<? super C> superClass, Class<C> creator);

    public abstract <C> void register(Map<Class<? super C>, Class<C>> theMap);

    public abstract <C> void registerInstance(Class<? super C> superClass, C instance);
}
