package br.com.monitoratec.farol.utils.data;

import java.security.SecureRandom;
import java.util.Random;

public class TokenGenerator {
    private static final int TOKEN_LENGTH = 255;
    private static final Random RANDOM = new SecureRandom();
    private static final char[] SYMBOLS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

    private TokenGenerator() {
    }

    public static String genNewToken() {
        final char[] buffer = new char[TOKEN_LENGTH];
        for (int idx = 0; idx < buffer.length; ++idx) {
            buffer[idx] = SYMBOLS[RANDOM.nextInt(SYMBOLS.length)];
        }
        return new String(buffer);
    }
}
