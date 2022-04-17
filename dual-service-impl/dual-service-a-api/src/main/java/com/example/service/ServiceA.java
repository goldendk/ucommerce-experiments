package com.example.service;

public interface ServiceA {

    /**
     * Calculates the sum of a and b.
     * @param a the first number
     * @param b the second number.
     * @return the sum of a and b. Cannot exceed {@link Integer#MAX_VALUE} or go below {@link Integer#MIN_VALUE}
     */
    int calculateSum(int a, int b);

}
