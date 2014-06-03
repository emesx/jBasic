package emesx.jbasic.backend.evaluators;

import emesx.jbasic.backend.SymbolTableStack;
import emesx.jbasic.intermediate.ir.Expression;

import java.util.HashMap;
import java.util.Map;

import static emesx.jbasic.utils.Utils.fmt;

public class EvaluationManager implements Evaluator<Expression> {
    private Map<Class<? extends Expression>, Evaluator<?>> evaluators;

    public EvaluationManager() {
        this.evaluators = new HashMap<>();
    }

    /**
     * Map an expression class to an evaluator instance
     */
    public <K extends Expression> EvaluationManager map(Class<K> clazz, Evaluator<K> evaluator) {
        evaluators.put(clazz, evaluator);
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object evaluate(Expression expression, SymbolTableStack symbolTableStack) {
        Evaluator evaluator = evaluators.get(expression.getClass());
        if (evaluator == null)
            throw new RuntimeException(fmt("Unexpected expression class: %s", expression.getClass()));

        return evaluator.evaluate(expression, symbolTableStack);
    }

}
