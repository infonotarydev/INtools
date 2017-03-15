package com.infonotary.INtools.IO;

/**
 * Binary-coded decimal tools
 */
class BCD {
    static byte[] toBcd(String s) {
        int size = s.length();
        int byteLen = size % 2 == 0 ? size / 2 : (size + 1) / 2;
        byte[] bytes = new byte[byteLen];
        int index = byteLen - (size + 1) / 2;
        boolean advance = size % 2 != 0;
        for (char c : s.toCharArray()) {
            byte b = (byte) (c - '0');
            if (advance) {
                bytes[index++] |= b;
            } else {
                bytes[index] |= (byte) (b << 4);
            }
            advance = !advance;
        }
        return bytes;
    }

    static byte[] DecimalToBCD(long num) {
        int digits = 0;

        long temp = num;
        while (temp != 0) {
            digits++;
            temp /= 10;
        }

        int byteLen = digits % 2 == 0 ? digits / 2 : (digits + 1) / 2;

        byte bcd[] = new byte[byteLen];

        for (int i = 0; i < digits; i++) {
            byte tmp = (byte) (num % 10);

            if (i % 2 == 0) {
                bcd[i / 2] = tmp;
            } else {
                bcd[i / 2] |= (byte) (tmp << 4);
            }

            num /= 10;
        }

        for (int i = 0; i < byteLen / 2; i++) {
            byte tmp = bcd[i];
            bcd[i] = bcd[byteLen - i - 1];
            bcd[byteLen - i - 1] = tmp;
        }

        return bcd;
    }

    public static long BCDToDecimal(byte[] bcd) {
        return Long.valueOf(BCD.BCDtoString(bcd));
    }

    private static String BCDtoString(byte bcd) {
        StringBuilder sb = new StringBuilder();

        byte high = (byte) (bcd & 0xf0);
        high >>>= (byte) 4;
        high = (byte) (high & 0x0f);
        byte low = (byte) (bcd & 0x0f);

        sb.append(high);
        sb.append(low);

        return sb.toString();
    }

    private static String BCDtoString(byte[] bcd) {
        StringBuilder sb = new StringBuilder();

        for (byte aBcd : bcd) {
            sb.append(BCDtoString(aBcd));
        }

        return sb.toString();
    }

    private static String byteArrayToBinaryString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte i : bytes) {
            String byteInBinary = String.format("%8s", Integer.toBinaryString(i)).replace(' ', '0');
            sb.append(byteInBinary);
        }
        return sb.toString();
    }
}