package br.com.monitoratec.farol.utils.data;

public class DataUtils {
    public static String onlyNumber(String input) {
        StringBuilder neededCharacters = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);

            if (Character.isDigit(ch)) {
                neededCharacters.append(input.charAt(i));
            }
        }

        return neededCharacters.toString();
    }

}
