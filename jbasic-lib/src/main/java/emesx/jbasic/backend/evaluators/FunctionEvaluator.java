package emesx.jbasic.backend.evaluators;

import emesx.jbasic.backend.SymbolTable;
import emesx.jbasic.backend.SymbolTableStack;
import emesx.jbasic.intermediate.ir.Expression;
import emesx.jbasic.intermediate.ir.expressions.FunctionExpression;
import emesx.jbasic.intermediate.ir.statements.DefStatement;

import java.util.ArrayList;
import java.util.List;

import static emesx.jbasic.utils.Utils.fmt;


public class FunctionEvaluator implements Evaluator<FunctionExpression> {
    private final Evaluator<Expression> parent;

    public FunctionEvaluator(Evaluator<Expression> parent) {
        this.parent = parent;
    }

    @Override
    public Object evaluate(FunctionExpression expression, SymbolTableStack symbols) {
        return evaluateFunction(expression, symbols);
    }

    private Object evaluateFunction(FunctionExpression fun, SymbolTableStack symbols) {
        final String funName = fun.getFunctionName().toUpperCase();
        final List<Object> arguments = new ArrayList<>();

        fun.getArguments().forEach(argExpr -> arguments.add(parent.evaluate(argExpr, symbols)));

        switch (funName) {
            case "ABS":
                return Math.abs((Double) arguments.get(0)); //TODO handle arg count mismatch
            case "ATAN":
                return Math.atan((Double) arguments.get(0));
            case "COS":
                return Math.cos((Double) arguments.get(0));
            case "EXP":
                return Math.exp((Double) arguments.get(0));
            case "INT":
                return ((Double) arguments.get(0)).intValue();
            case "LOG":
                return Math.log((Double) arguments.get(0));
            case "RND":
                return Math.random();
            case "SIN":
                return Math.sin((Double) arguments.get(0));
            case "SQR":
                return Math.sqrt((Double) arguments.get(0));
            case "TAN":
                return Math.tan((Double) arguments.get(0));

            // Functions not present in Dartmouth Basic
            case "LOG10":
                return Math.log10((Double) arguments.get(0));
            case "MAX":
                return Math.max((Double) arguments.get(0), (Double) arguments.get(1));
            case "MIN":
                return Math.min((Double) arguments.get(0), (Double) arguments.get(1));
            case "RAD":
                return Math.toRadians((Double) arguments.get(0));


            // User-defined functions
            default:
                return evaluateUserFunction(fun, symbols);
        }
    }

    private Object evaluateUserFunction(FunctionExpression fun, SymbolTableStack scopes) {
        if (!scopes.contains(fun.getFunctionName()))
            //TODO own exceptions; should point to line:column; use Dartmouth messages
            throw new RuntimeException(fmt("Undeclared function `%s`", fun.getFunctionName()));

        DefStatement def = (DefStatement) scopes.get(fun.getFunctionName());

        if (def.getParameters().size() != fun.getArguments().size())
            throw new RuntimeException(fmt("Invalid argument count in `%s` invocation; expecting %d", fun.getFunctionName(), def.getParameters().size()));

        SymbolTable functionSymbolTable = new SymbolTable();
        for (int i = 0; i < def.getParameters().size(); ++i) {
            functionSymbolTable.set(
                    def.getParameters().get(i),
                    parent.evaluate(fun.getArguments().get(i), scopes)
            );
        }

        scopes.pushTable(functionSymbolTable);
        Object result = parent.evaluate(def.getExpression(), scopes);
        scopes.popTable();

        return result;
    }


}
