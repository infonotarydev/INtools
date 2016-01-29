package com.infonotary.INtools.IO;

import java.nio.ByteBuffer;

public class IOTools {
    static final char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }

        /*
        //alternative way, worse
        StringBuilder sb = new StringBuilder();
        for (byte aDigest1 : bytes) {
            sb.append(Integer.toString((aDigest1 & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString().toUpperCase();
        */

        return (new String(hexChars)).toLowerCase();
    }

    public static byte[] hexToBytes(String digits) {
        digits = digits.replace(" ", "");
        final int bytes = digits.length() / 2;
        if (2 * bytes != digits.length()) {
            throw new IllegalArgumentException("Hex string must have an even number of digits");
        }
        byte[] result = new byte[bytes];
        for (int i = 0; i < digits.length(); i += 2) {
            result[i / 2] = (byte) Integer.parseInt(digits.substring(i, i + 2), 16);
        }
        return result;
    }

    public static byte[] intToBytes(int i) {
        return ByteBuffer.allocate(4).putInt(i).array();
    }

    public static byte[] shortToBytes(short s) {
        return ByteBuffer.allocate(2).putShort(s).array();
    }

    public static String intToHex(int i) {
        return bytesToHex(intToBytes(i));
    }

    public static String shortToHex(short s) {
        return bytesToHex(shortToBytes(s));
    }

    public static String asciiToHex(String ascii) {
        StringBuilder hex = new StringBuilder();
        for (int i = 0; i < ascii.length(); i++) {
            hex.append(Integer.toHexString(ascii.charAt(i)));
        }
        return IOMisc.formatHexString(hex.toString()).toLowerCase();
    }

    public static String asciiToHexNoSpaces(String ascii) {
        StringBuilder hex = new StringBuilder();
        for (int i = 0; i < ascii.length(); i++) {
            hex.append(Integer.toHexString(ascii.charAt(i)));
        }
        return hex.toString().toLowerCase();
    }
}