/**
 * Package for all annotation processors used by the UCommerce project.
 *
 * <p>
 * <h2>ExternalServiceProcessor</h2>
 * <p>
 * Has the responsibility for generating source code based on an external UCommerce service.
 * The following sources will be generated.
 * <ul>
 * <li>OAS 3.0 specification</li>
 * <li>Swagger UI configuration</li>
 * <li>Spring MVC REST Controller and associated POJO classes</li>
 * <li>Java Proxy API definition (direct method calls) with appropriate documentation (remote service exception).</li>
 * <li>Remote REST client implementation with appropriate marshalling between the rest-endpoints and java methods. The point of this should be that the client modules can use OOP design.</li>
 * </ul>
 *
 * <h3>Resources</h3>
 * <ul>
 * <li>Google library : https://javadoc.io/doc/com.google.testing.compile/compile-testing/latest/index.html</li>
 * <li>Example usage : https://github.com/tommy-bo/junit-ignore-processor</li>
 * </ul>
 *
 *
 * </p>
 */
package org.ucommerce.library.annotation.processor;
