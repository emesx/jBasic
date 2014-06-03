package emesx.jbasic.backend;

import emesx.jbasic.intermediate.ir.Statement;
import emesx.jbasic.utils.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Queue;
import java.util.Stack;

import static emesx.jbasic.utils.Utils.fmt;

public class Execution {
    private static final Logger LOG = LoggerFactory.getLogger(Execution.class);

    private final Stack<Reference<Statement>> continuations;
    private final Data data;
    private boolean complete;
    private boolean interrupted;

    public Execution(List<Statement> statements, Data data) {
        this.continuations = new Stack<>();
        this.continuations.push(new Reference<>(statements.get(0)));
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void complete() {
        LOG.debug("Completing execution");
        complete = true;
    }

    public boolean isComplete() {
        return complete || continuations.isEmpty() || continuations.peek().get() == null;
    }

    public boolean isInterrupted() {
        return interrupted;
    }

    public void interrupt() {
        LOG.debug("Interrupting execution");
        interrupted = true;
    }

    public Statement proceed() {
        Statement current = continuations.peek().get();
        continuations.peek().set(current.getNext());
        interrupted = false;

        return current;
    }

    public void jump(int statementNumber) {
        Statement statement = find(statementNumber);
        continuations.peek().set(statement);
    }

    public void startContinuation(int statementNumber) {
        Statement statement = find(statementNumber);
        continuations.push(new Reference<>(statement));
    }

    public void restoreContinuation() {
        continuations.pop();
        interrupted = false;
    }

    private Statement find(int statementNumber) {
        Statement current = continuations.peek().get();

        if (current.getNumber() == statementNumber)
            return current;

        else if (statementNumber > current.getNumber())
            while (current != null && current.getNumber() < statementNumber)
                current = current.getNext();

        else
            while (current != null && current.getNumber() > statementNumber)
                current = current.getPrevious();

        if (current == null)
            throw new RuntimeException(fmt("Cannot jump to statement %d : not found", statementNumber));

        return current;
    }

    
    public static class Data {
        private final Queue<Double> data;

        public Data(Queue<Double> data) {
            this.data = data;
        }

        public double next() {
            return data.remove();
        }

        public boolean hasNext() {
            return !data.isEmpty();
        }
    }
}
