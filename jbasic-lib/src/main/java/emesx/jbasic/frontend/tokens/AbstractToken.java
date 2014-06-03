package emesx.jbasic.frontend.tokens;

public abstract class AbstractToken<T> implements Token<T> {
    private final T value;

    public AbstractToken(T value) {
        this.value = value;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public String getString() {
        return String.valueOf(getValue());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + getString() + "}";
    }
}
