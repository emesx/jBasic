package emesx.jbasic.utils;

public class Reference<T> {
    private T target;

    public Reference(T target) {
        this.target = target;
    }

    public T get() {
        return target;
    }

    public void set(T target) {
        this.target = target;
    }
}
