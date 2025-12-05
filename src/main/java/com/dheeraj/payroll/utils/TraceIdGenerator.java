package com.dheeraj.payroll.utils;

import java.security.SecureRandom;

public class TraceIdGenerator {

    private TraceIdGenerator() {
        //Prevent instantiation
    }

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final char[] ENCODING_CHARS =
            "0123456789ABCDEFGHJKMNPQRSTVWXYZ".toCharArray();

    public static String generateTraceId() {
        long time = System.currentTimeMillis();
        char[] buffer = new char[26];

        //Encode timestamp (48 bits -> 10 chars)
        for (int i = 9; i >=0; i--) {
            buffer[i] = ENCODING_CHARS[(int) (time & 31)];
            time >>>= 5;
        }

        //Encode randomness (80 bits -> 16 chars)
        for (int i = 10; i < 26; i++) {
            buffer[i] = ENCODING_CHARS[RANDOM.nextInt(32)];
        }

        return new String(buffer);

    }
}
