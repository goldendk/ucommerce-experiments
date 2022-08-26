package org.ucommerce.shared.kernel.extension;

import java.io.Serializable;

/**
 * Generic object extension. It is the application developer's responsibility that the object stored is serializable and
 * can be recovered from it's serialised state.
 */
public class ObjectDataExtension extends UDataExtension<Object> {

    public ObjectDataExtension(Class<Object> type) {
        super(type);
    }

}
