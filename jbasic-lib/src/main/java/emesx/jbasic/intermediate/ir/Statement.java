package emesx.jbasic.intermediate.ir;


import java.util.function.Consumer;

public abstract class Statement {
    private int number;

    private Statement previous;
    private Statement next;

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public Statement getPrevious() {
        return previous;
    }

    public Statement getNext() {
        return next;
    }

    public void setPrevious(Statement previous) {
        this.previous = previous;
    }

    public void setNext(Statement next) {
        this.next = next;
    }

    public Statement getLast() {
        Statement last = this;
        while (last.next != null)
            last = last.next;

        return last;
    }

    public Statement getFirst() {
        Statement first = this;
        while (first.previous != null)
            first = first.previous;

        return first;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "number=" + number + "}";
    }

    /**
     * Perform an action on all statements starting from this incl. all nested (e.g. in FOR nodes) statements.
     */
    public void forEachDeep(Consumer<Statement> consumer) {
        Statement current = this;
        do {
            consumer.accept(current);
            current = current.getNext();
        } while (current != null);
    }
}
