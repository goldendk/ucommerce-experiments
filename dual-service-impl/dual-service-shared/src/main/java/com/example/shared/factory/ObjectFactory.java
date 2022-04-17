package com.example.shared.factory;

import com.example.shared.CachedObjectFactory;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class ObjectFactory {

    public static ObjectFactory createFactory() {
        return new CachedObjectFactory();
    }


    public abstract <T> T createObject(Class<T> clazz);

    public abstract void register(Class superClass, Class creator);

    public abstract void register(Map<Class, Class> theMap);

}
