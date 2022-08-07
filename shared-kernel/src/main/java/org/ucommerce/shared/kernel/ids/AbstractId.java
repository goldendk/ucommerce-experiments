package org.ucommerce.shared.kernel.ids;

public class AbstractId<T> {

    private final T value;

    public AbstractId(T value) {
        this.value = value;
    }

    public T getValue(){
        return value;
    }
}
