package org.ucommerce.shared.kernel.extension;

import org.ucommerce.shared.kernel.UAudit;

import java.io.Serializable;
import java.util.Objects;

/**
 * General extension format for business objects in the UCommerce framework. Business objects should add new properties using
 * subclasses of this class instead of extending the persistence schema.
 *
 * <p>
 * Application specific subclasses of this class needs to provide their own marshall/unmarshall logic.
 * <p>
 * //FIXME: Add reference to how to implement custom subclass of UDataExtension.
 *
 * @param <T> the type of the data extension.
 */
public abstract class UDataExtension<T> {
    private T value;
    private Class<T> type;
    private UAudit audit;

    public UDataExtension(Class<T> type) {
        this.type = Objects.requireNonNull(type, "Type cannot be null.");
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Class<T> getType() {
        return type;
    }

    public UAudit getAudit() {
        return audit;
    }
}
