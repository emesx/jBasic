package emesx.jbasic.frontend.tokens;

import java.util.Arrays;

public enum Operator implements Token<Operator> {
    SEPARATOR(","),
    ADDITION("+"),
    SUBTRACTION("-"),
    MULTIPLICATION("*"),
    DIVISION("/"),
    EXPONENTIATION("^"),
    EQUAL_TO("="),
    NOT_EQUAL_TO("<>"),
    LESS_THAN("<"),
    LESS_THAN_EQUAL("<="),
    GREATER_THAN(">"),
    GREATER_THAN_EQUAL(">="),
    LEFT_PARENTHESIS("("),
    RIGHT_PARENTHESIS(")"),
    STRING_DELIMITER("\"");

    private final String string;

    private Operator(String string) {
        this.string = string;
    }

    @Override
    public Operator getValue() {
        return this;
    }

    @Override
    public String getString() {
        return string;
    }

    public boolean startsWith(char firstChar) {
        return string.charAt(0) == firstChar;
    }

    public static boolean mayStartWith(char firstChar) {
        return mayStartWith(String.valueOf(firstChar));
    }

    public static boolean mayStartWith(String firstChars) {
        return Arrays.stream(values())
                .anyMatch(operator -> operator.getString().startsWith(firstChars));
    }

    public static Operator fromString(String string) {
        return Arrays.stream(values())
                .filter(operator -> operator.getString().equals(string))
                .findFirst()
                .get();
    }
}
