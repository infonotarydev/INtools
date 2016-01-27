package com.infonotary.INtools.SmartCard;

import com.infonotary.INtools.IO.IOMisc;

public class CommandTools {
    public static String cmdSelectFile(String strFID, P1input inputP1) {
        String len = IOMisc.countBytesInHex(strFID);
        String p1 = inputP1.value;
        return "00 A4 " + p1 + " 00 " + len + " " + strFID;
    }

    public enum P1input {
        FID("00"), AID("04"), ABSOLUTE("08"), RELATIVE("09");
        protected String value;

        P1input(String value) {
            this.value = value;
        }
    }
}