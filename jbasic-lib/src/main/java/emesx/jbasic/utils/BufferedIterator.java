package emesx.jbasic.utils;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;


public abstract class BufferedIterator<T> implements Iterator<T> {
    private Optional<T> buffer = Optional.empty();

    @Override
    public final boolean hasNext() {
        if (buffer.isPresent())
            return true;

        try {
            buffer = Optional.of(next());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public final T peek() throws NoSuchElementException {
        hasNext();
        return buffer.get();
    }

    @Override
    public final T next() {
        if (buffer.isPresent()) {
            T next = buffer.get();
            buffer = Optional.empty();
            return next;
        }

        return getNext();
    }

    protected abstract T getNext();
}
