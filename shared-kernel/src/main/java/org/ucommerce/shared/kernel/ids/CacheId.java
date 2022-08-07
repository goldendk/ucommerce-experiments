package org.ucommerce.shared.kernel.ids;

public class CacheId extends AbstractId<String> {

    private CacheId(String value) {
        super(value);
    }

    public static CacheId of(String value){
        return new CacheId(value);
    }


}
