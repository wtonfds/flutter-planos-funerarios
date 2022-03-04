package br.com.monitoratec.farol.utils.password;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class PasswordUtils {
    private static final char[] LOWER_CASE_LETTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final char[] UPPER_CASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final char[] NUMBERS = "0123456789".toCharArray();
    private static final char[] SYMBOLS = "!\"#$%'()*&,-./".toCharArray();

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public String hashPassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public boolean doesPasswordMatch(String digestHash, String passwordToCheck) {
        return bCryptPasswordEncoder.matches(passwordToCheck, digestHash);
    }

    public String generateRandom() {
        final Random random = new SecureRandom();
        final char[] newPassword = generateRandomArray(random);

        //Shuffle it
        for (int i = newPassword.length; i > 1; i--) {
            swap(newPassword, i - 1, random.nextInt(i));
        }

        return new String(newPassword);
    }

    public String generateRandomAuthToken() {
        final Random random = new SecureRandom();
        final char[] newAuthToken = generateAuthToken(random);

        //Shuffle it
        for (int i = newAuthToken.length; i > 1; i--) {
            swap(newAuthToken, i - 1, random.nextInt(i));
        }

        return new String(newAuthToken);
    }

    //Generate an array with 12 random characters, being 3 lower-case letters, 3 upper-case, 3 numbers and 3 symbols
    private char[] generateRandomArray(Random random) {
        final char[] newPassword = new char[12];
        newPassword[0] = LOWER_CASE_LETTERS[random.nextInt(LOWER_CASE_LETTERS.length)];
        newPassword[1] = LOWER_CASE_LETTERS[random.nextInt(LOWER_CASE_LETTERS.length)];
        newPassword[2] = LOWER_CASE_LETTERS[random.nextInt(LOWER_CASE_LETTERS.length)];
        newPassword[3] = UPPER_CASE_LETTERS[random.nextInt(UPPER_CASE_LETTERS.length)];
        newPassword[4] = UPPER_CASE_LETTERS[random.nextInt(UPPER_CASE_LETTERS.length)];
        newPassword[5] = UPPER_CASE_LETTERS[random.nextInt(UPPER_CASE_LETTERS.length)];
        newPassword[6] = NUMBERS[random.nextInt(NUMBERS.length)];
        newPassword[7] = NUMBERS[random.nextInt(NUMBERS.length)];
        newPassword[8] = NUMBERS[random.nextInt(NUMBERS.length)];
        newPassword[9] = SYMBOLS[random.nextInt(SYMBOLS.length)];
        newPassword[10] = SYMBOLS[random.nextInt(SYMBOLS.length)];
        newPassword[11] = SYMBOLS[random.nextInt(SYMBOLS.length)];

        return newPassword;
    }

    private char[] generateAuthToken(Random random) {
        final char[] newPassword = new char[4];
        newPassword[0] = LOWER_CASE_LETTERS[random.nextInt(LOWER_CASE_LETTERS.length)];
        newPassword[1] = UPPER_CASE_LETTERS[random.nextInt(UPPER_CASE_LETTERS.length)];
        newPassword[2] = NUMBERS[random.nextInt(NUMBERS.length)];
        newPassword[3] = SYMBOLS[random.nextInt(SYMBOLS.length)];

        return newPassword;
    }

    //Copied from java.util.Arrays
    private void swap(char[] array, int a, int b) {
        char t = array[a];
        array[a] = array[b];
        array[b] = t;
    }
}
