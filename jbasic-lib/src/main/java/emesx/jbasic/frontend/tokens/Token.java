package emesx.jbasic.frontend.tokens;

public interface Token<T> {
    T getValue();

    String getString();

    //String getLineColumn(); TODO IMPLEMENT LINE:COL
}
