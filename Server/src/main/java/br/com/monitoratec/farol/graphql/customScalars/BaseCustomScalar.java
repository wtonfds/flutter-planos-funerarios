package br.com.monitoratec.farol.graphql.customScalars;

public class BaseCustomScalar {

    protected static String typeName(Object input) {
        if (input == null) {
            return "null";
        }

        return input.getClass().getSimpleName();
    }

    protected static boolean isNumberIsh(Object input) {
        return input instanceof Number || input instanceof String;
    }
}
