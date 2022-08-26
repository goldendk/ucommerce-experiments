package org.ucommerce.modules.inventory.services;

import org.ucommerce.modules.inventory.model.AtpRequestFlag;

import java.util.Arrays;
import java.util.Objects;

/**
 * Helper class for working with AtpRequestFlags.
 */
public class AtpRequestFlagHelper {
    /**
     * Disables the flag (by removing it).
     *
     * @param flagId the flagId to remove.
     * @param flags  the array to remove flag with 'flagId' from.
     * @return a new array where the flagId has been removed. The original array is unchanged.
     */
    public static AtpRequestFlag[] disable(String flagId, AtpRequestFlag[] flags) {
        return Arrays.stream(flags).filter(e -> !Objects.equals(flagId, e.id())).toArray(AtpRequestFlag[]::new);
    }

    /**
     * Indicates if the given array contains a flag with the given id.
     *
     * @param flagId the id to search for.
     * @param flags  the flag array
     * @return True if one or more flags have the flagId
     */
    public static boolean contains(String flagId, AtpRequestFlag[] flags) {
        return Arrays.stream(flags).filter(e -> Objects.equals(flagId, e.id())).findFirst().isPresent();
    }
}
