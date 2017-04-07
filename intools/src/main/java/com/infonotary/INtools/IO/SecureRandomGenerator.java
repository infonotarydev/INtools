package com.infonotary.INtools.IO;

import java.security.SecureRandom;

public class SecureRandomGenerator {

    private static SecureRandomGenerator instance = new SecureRandomGenerator();
    private static SecureRandom random = new SecureRandom();

    private SecureRandomGenerator() {
    }

    public static SecureRandomGenerator getInstance() {
        return instance;
    }

    public byte[] getRand(int numBytes) {
        byte[] rndBytes = new byte[numBytes];
        random.nextBytes(rndBytes);
        return rndBytes;
    }

    /**
     * Generates a random integer from 0 to the maximum specifies value
     *
     * @param maxValue Maximum value for the random number
     * @return random number
     */
    public int getRandInt(int maxValue) {
        return random.nextInt(maxValue);
    }

}