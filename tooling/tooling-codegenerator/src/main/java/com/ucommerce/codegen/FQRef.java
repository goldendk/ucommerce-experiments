package com.ucommerce.codegen;

/**
 * A fully qualified reference to a source file. It may not be compiled yet.
 * @param packagePath the path of the package e.g. "org.ucommerce.modules.inventory"
 * @param className the class name e.g. "InventoryService"
 */
public record FQRef(String packagePath, String className) {
}
