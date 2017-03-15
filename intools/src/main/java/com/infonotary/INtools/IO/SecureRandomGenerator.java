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

}