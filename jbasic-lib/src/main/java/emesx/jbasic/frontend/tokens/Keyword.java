package emesx.jbasic.frontend.tokens;

public enum Keyword implements Token<Keyword> {
    DEF,
    DIM,
    END,
    STOP,
    FOR,
    TO,
    STEP,
    NEXT,
    GOSUB,
    RETURN,
    GOTO,
    IF,
    THEN,
    LET,
    PRINT,
    DATA,
    READ,
    REM;

    @Override
    public Keyword getValue() {
        return this;
    }

    @Override
    public String getString() {
        return toString();
    }
}
