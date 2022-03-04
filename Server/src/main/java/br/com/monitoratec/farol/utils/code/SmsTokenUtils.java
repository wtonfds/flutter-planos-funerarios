package br.com.monitoratec.farol.utils.code;

import java.security.SecureRandom;
import java.util.Random;

public class SmsTokenUtils {

    private static final char[] NUMBERS = "0123456789".toCharArray();

    public String generateRandomSmsCode() {
        final Random random = new SecureRandom();
        final char[] newPassword = generateRandomArray(random);

        return new String(newPassword);
    }


    private char[] generateRandomArray(Random random) {
        final char[] code = new char[6];
        code[0] = NUMBERS[random.nextInt(NUMBERS.length)];
        code[1] = NUMBERS[random.nextInt(NUMBERS.length)];
        code[2] = NUMBERS[random.nextInt(NUMBERS.length)];
        code[3] = NUMBERS[random.nextInt(NUMBERS.length)];
        code[4] = NUMBERS[random.nextInt(NUMBERS.length)];
        code[5] = NUMBERS[random.nextInt(NUMBERS.length)];
        return code;
    }

}
