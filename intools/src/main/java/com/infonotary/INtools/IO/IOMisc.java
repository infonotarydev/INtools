package com.infonotary.INtools.IO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class IOMisc {
    private static boolean isHexStringChar(char c) {
        return (Character.isDigit(c) || Character.isWhitespace(c) || (("0123456789abcdefABCDEF".indexOf(c)) >= 0));
    }

    /**
     * Return true if the argument string seems to be a
     * Hex data string, like "a0 13 2f ". Whitespace is
     * ignored.
     */
    public static boolean isHex(String sampleData) {
        for (int i = 0; i < sampleData.length(); i++) {
            if (!isHexStringChar(sampleData.charAt(i))) return false;
        }
        return true;
    }

    public static String formatHexString(String s) {
        String hex = s.replaceAll(".{2}", "$0 ");
        return hex.substring(0, hex.length() - 1);
    }

    public static String countBytesInHex(String hexStr) {
        int hexStrLen = 0;
        boolean prevCharWasSpace = true;
        for (int i = 0; i < hexStr.length(); i++) {
            if (hexStr.charAt(i) == ' ') {
                prevCharWasSpace = true;
            } else {
                if (prevCharWasSpace) {
                    hexStrLen++;
                }
                prevCharWasSpace = false;
            }
        }
        String len = Integer.toHexString(hexStrLen);
        if (len.length() < 2) {
            len = '0' + len;
        }
        return len;
    }

    public static List<String> removeDuplicates(List<String> input) {
        HashSet<String> listToSet = new HashSet<>(input);
        return new ArrayList<>(listToSet);
    }

    public static String generateRandom(int length) {
        SecureRandomGenerator generator = SecureRandomGenerator.getInstance();
        byte[] result = generator.getRand(length);
        return new String(result);
    }
}