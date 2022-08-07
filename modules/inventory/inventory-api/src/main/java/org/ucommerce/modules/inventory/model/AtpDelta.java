package org.ucommerce.modules.inventory.model;

/**
 * An ATP value is a result of several modifiers stacked on top of each other.
 * @param amount
 * @param source
 * @param operation
 */
public record AtpDelta(long amount, String source, AtpOperation operation) {

}
