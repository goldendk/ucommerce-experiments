package org.ucommerce.shared.kernel.services;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Represents an external service API in the UCommerce project. Enables the interface to targeted by code-generators and
 * annotation-processors. Should only be applied to interfaces.
 *
 * <p>
 *     Interfaces marked with this annotation should adhere to certain limitations on the Java language to support code-generation.
 *
 *     <ul>
 *         <li>Use records as parameters objects</li>
 *         <li>Use simple types as method parameters (String, int, double, LocalDate etc).</li>
 *         <li>DO NOT use object graphs as parameter objects. All data sent to an {@link ExternalService} should be "data-trees", not graphs.
 *         This is to support serialisation (ie. REST interfaces).</li>
 *     </ul>
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ExternalService {
}
