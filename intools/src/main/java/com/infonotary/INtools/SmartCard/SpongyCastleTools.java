package com.infonotary.INtools.SmartCard;

import com.infonotary.INtools.IO.IOMisc;
import com.infonotary.INtools.IO.IOTools;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.BERTags;
import org.spongycastle.asn1.DERApplicationSpecific;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.util.ASN1Dump;
import org.spongycastle.asn1.x500.AttributeTypeAndValue;
import org.spongycastle.asn1.x500.RDN;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x500.style.BCStyle;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.security.auth.x500.X500Principal;

public class SpongyCastleTools {

    public static String decodeDER(byte[] input) {                                                  //for debug purposes
        String output = null;
        boolean isFirst = true;
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(input);
            ASN1InputStream bIn = new ASN1InputStream(byteArrayInputStream);
            ASN1Primitive obj = bIn.readObject();
            String objStr = ASN1Dump.dumpAsString(obj);                                             //shows structure only
            System.out.println(objStr);
            DERApplicationSpecific app = (DERApplicationSpecific) obj;
            ASN1Sequence asn1Sequence = (ASN1Sequence) app.getObject(BERTags.SEQUENCE);
            Enumeration secEnum = asn1Sequence.getObjects();
            while (secEnum.hasMoreElements()) {
                ASN1Primitive seqObj = (ASN1Primitive) secEnum.nextElement();
                if (isFirst) {
                    output = seqObj.toString();
                    isFirst = false;
                } else {
                    output = output + " " + seqObj.toString();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            output = e.getMessage();
        }
        return output;
    }

    public static String decodeDERtoFileID(byte[] input, int targetTag) {
        String fileID = null;
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(input);
            ASN1InputStream bIn = new ASN1InputStream(byteArrayInputStream);
            ASN1Primitive obj = bIn.readObject();
            ASN1Sequence asn1Sequence = (ASN1Sequence) obj;
            Enumeration secEnum = asn1Sequence.getObjects();
            while (secEnum.hasMoreElements()) {
                ASN1Primitive seqObj = (ASN1Primitive) secEnum.nextElement();
                if (seqObj instanceof DERTaggedObject) {
                    DERTaggedObject derTag = (DERTaggedObject) seqObj;
                    int tagInt = derTag.getTagNo();
                    if (tagInt == targetTag) {
                        ASN1Primitive fileIDtemp1 = derTag.getObject();
                        ASN1Sequence asn1Seq = (ASN1Sequence) fileIDtemp1;
                        ASN1Encodable asn1Enc = asn1Seq.getObjectAt(0);
                        boolean isNeededOctet = false;
                        while (!isNeededOctet) {
                            try {
                                ASN1Sequence asn1Seq2 = (ASN1Sequence) asn1Enc;
                                asn1Enc = asn1Seq2.getObjectAt(0);
                            } catch (Exception e) {
                                isNeededOctet = true;
                            }
                        }
                        fileID = IOMisc.formatHexString(valueStr(asn1Enc.toString()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileID;
    }

    public static String decodeDERtoMatchID(byte[] input, int targetCounter) {
        String fileID = null;
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(input);
            ASN1InputStream bIn = new ASN1InputStream(byteArrayInputStream);
            ASN1Primitive obj = bIn.readObject();
            ASN1Sequence asn1Sequence = (ASN1Sequence) obj;
            Enumeration secEnum = asn1Sequence.getObjects();
            int elementCounter = 0;
            while (secEnum.hasMoreElements()) {
                elementCounter++;
                ASN1Primitive seqObj = (ASN1Primitive) secEnum.nextElement();
                if (elementCounter == targetCounter) {
                    ASN1Sequence asn1Seq = (ASN1Sequence) seqObj;
                    ASN1Encodable asn1Enc = asn1Seq.getObjectAt(0);
                    boolean isNeededOctet = false;
                    while (!isNeededOctet) {
                        try {
                            ASN1Sequence asn1Seq2 = (ASN1Sequence) asn1Enc;
                            asn1Enc = asn1Seq2.getObjectAt(0);
                        } catch (Exception e) {
                            isNeededOctet = true;
                        }
                    }
                    fileID = IOMisc.formatHexString(valueStr(asn1Enc.toString()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileID;
    }

    public static String decodeDERtoSmallStrings(byte[] input) {
        String fileStr;
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(input);
            ASN1InputStream bIn = new ASN1InputStream(byteArrayInputStream);
            ASN1Primitive obj = bIn.readObject();
            byte[] fileBytes = obj.getEncoded();
            fileStr = IOTools.bytesToHex(fileBytes);
        } catch (IOException e) {
            e.printStackTrace();
            fileStr = e.getMessage();
        }
        return fileStr;
    }

    public static String decodeDERappSpecific(byte[] input, int targetTag) {
        String output = null;
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(input);
            ASN1InputStream bIn = new ASN1InputStream(byteArrayInputStream);
            ASN1Primitive obj = bIn.readObject();
            DERApplicationSpecific derApplicationSpecific = (DERApplicationSpecific) obj;
            byte[] derApp = derApplicationSpecific.getContents();
            String derAppStr = IOTools.bytesToHex(derApp);
            List<String> resultFiles = new ArrayList<String>();
            while (derAppStr.length() > 0) {
                String file = decodeDERtoSmallStrings(IOTools.hexToBytes(derAppStr));
                resultFiles.add(file);
                derAppStr = derAppStr.substring(file.length(), derAppStr.length());
            }
            for (String str : resultFiles) {
                byte[] strByte = IOTools.hexToBytes(str);
                ByteArrayInputStream byteArrayInputStream2 = new ByteArrayInputStream(strByte);
                ASN1InputStream bIn2 = new ASN1InputStream(byteArrayInputStream2);
                ASN1Primitive obj2 = bIn2.readObject();
                if (obj2 instanceof DERTaggedObject) {
                    DERTaggedObject derTaggedObject = (DERTaggedObject) obj2;
                    int tagNo = derTaggedObject.getTagNo();
                    if (tagNo == targetTag) {
                        output = derTaggedObject.getObject().toString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valueStr(output);
    }

    public static String getCertEmail(X509Certificate cert) {
        String certEmail = null;
        X500Principal principal = cert.getSubjectX500Principal();
        X500Name x500name = new X500Name(principal.getName());
        RDN cn = x500name.getRDNs()[0];
        ASN1Primitive asn1Primitive1 = cn.toASN1Primitive();
        ASN1Set values = (ASN1Set) asn1Primitive1;
        Enumeration certEnum = values.getObjects();
        while (certEnum.hasMoreElements()) {
            AttributeTypeAndValue a = (AttributeTypeAndValue) certEnum.nextElement();
            ASN1ObjectIdentifier id = a.getType();
            if (id.toString().equals(BCStyle.EmailAddress.toString())) {
                certEmail = a.getValue().toString();
            }
        }
        return certEmail;
    }

    private static String valueStr(String str) {
        int startIndex = str.indexOf("#");
        return str.substring(startIndex + 1, str.length());
    }
}