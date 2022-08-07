package org.ucommerce.shared.kernel.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.*;

public class DefaultObjectFactory extends ObjectFactory {

    private static final Map<Class, Class> implementationMap = new HashMap<>();

    public <T> T createObject(Class<T> clazz) {
        Objects.requireNonNull(clazz, "Class to lookup cannot be null");

        Class typeToConstruct = implementationMap.get(clazz);
        if(typeToConstruct == null){
            throw new IllegalStateException("Type to construct is null, asked to construct: " + clazz.getName());
        }

        Constructor<?> constToUse = null;
        Constructor<?>[] constructors = typeToConstruct.getConstructors();

        constructorLoop:
        for (Constructor<?> con : constructors) {
            if (con.getParameterCount() == 0) {
                constToUse = con;
                break;
            }
            for (Class<?> paramType : con.getParameterTypes()) {
                if (!implementationMap.containsKey(paramType)) {
                    continue constructorLoop;
                }
            }

            constToUse = con;
            break constructorLoop;
        }

        if (constToUse == null) {
            throw new IllegalStateException("No fitting constructor found for " + typeToConstruct);
        }

        List<Object> params = new ArrayList<>();

        for (Class<?> type : constToUse.getParameterTypes()) {
            params.add(createObject(type));
        }

        try {
            return (T) constToUse.newInstance(params.toArray(new Object[0]));
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }


    }

    public <C> void register(Class<? super C> superClass, Class<C> toCreate) {
        Objects.requireNonNull(superClass);
        Objects.requireNonNull(toCreate);

        Class<?>[] interfaces = toCreate.getInterfaces();

        for (Class<?> i : interfaces) {
            boolean assignableFrom = i.isAssignableFrom(toCreate);
            if (!assignableFrom) {
                throw new IllegalStateException(MessageFormat.format(
                        "Attempting to register object of type [{0}] that is not assignable from [{1}]",
                        toCreate.getName(),
                        superClass.getName()));
            }
        }

        implementationMap.put(superClass, toCreate);
    }


    public <C> void register(Map<Class<? super C>, Class<C>> theMap) {
        theMap.forEach((k, v) -> {
            register(k, v);
        });
    }

}

