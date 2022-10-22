package org.ucommerce.modules.orders.service;

/**
 * Handles the major transitions between the (default) order-states.
 * The order processing is divided into the following segments
 * <ol>
 *      <li>Product selection</li>
 *      <li>Delivery selection</li>
 *      <li>Customer information</li>
 *      <li>Payment processing</li>
 *      <li>Fulfilment</li>
 *      <li>Payment completion</li>
 *      <li>Shipping</li>
 *      <li>Final delivery</li>
 * </ol>
 *
 * Beyond the basic order flows there are several actions available during each of the states mentioned above.
 *
 * <ul>
 *     <li>Cancelling product selection</li>
 *     <li>Cancelling order</li>
 * </ul>
 *
 */
public class BackBone {



}
