package com.dxt.common.util;

import com.dxt.common.exception.ErrorCode;
import com.dxt.common.exception.SystemException;

import java.security.MessageDigest;

public class Md5Api {

    private static final String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return byteArrayToHexString(md.digest(input.getBytes("UTF-8")));
        } catch (Exception e) {
            throw new SystemException(ErrorCode.CALC_MD5_ERROR).setCause(e);
        }
    }

    protected static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    protected static String byteToHexString(byte b) {
        int n = b;
        if (n < 0){
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
}
