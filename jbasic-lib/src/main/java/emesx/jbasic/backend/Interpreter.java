package emesx.jbasic.backend;

import emesx.jbasic.backend.interpreters.BasicInterpreter;
import emesx.jbasic.intermediate.ir.Program;
import emesx.jbasic.intermediate.ir.Statement;
import emesx.jbasic.intermediate.ir.statements.DataStatement;
import emesx.jbasic.intermediate.ir.statements.DefStatement;
import emesx.jbasic.utils.Validator;

import java.util.*;

import static emesx.jbasic.utils.Utils.fmt;

public class Interpreter {
    private final BasicInterpreter basicInterpreter;

    public Interpreter() {
        this.basicInterpreter = new BasicInterpreter();
    }


    public void run(Program program) {
        run(program, new SymbolTable());
    }

    public void run(Program program, SymbolTable symbolTable) {
        validate(program);

        List<Statement> main = program.getMain();
        Execution execution = new Execution(main, extractData(main));
        SymbolTableStack symbols = new SymbolTableStack(symbolTable);
        extractDefs(main, symbols);

        basicInterpreter.interpret(execution, symbols);
    }


    private void validate(Program program) {
        Validator.requireNonNull(program.getMain(), "Program is null");

        if (program.getMain().isEmpty())
            return;

        Set<Integer> lines = new HashSet<>();

        program.getMain().get(0).forEachDeep(statement -> {
            if (!lines.add(statement.getNumber()))
                throw new RuntimeException(fmt("Statement no. %d occurs multiple times", statement.getNumber()));
        });
    }

    private void extractDefs(List<Statement> main, SymbolTableStack scopes) {
        main.stream()
                .filter(statement -> statement instanceof DefStatement)
                .map(statement -> (DefStatement) statement)
                .forEachOrdered(defStatement -> scopes.set(defStatement.getName(), defStatement));
    }

    private Execution.Data extractData(List<Statement> main) {
        final Queue<Double> data = new LinkedList<>();

        main.stream()
                .filter(statement -> statement instanceof DataStatement)
                .map(statement -> (DataStatement) statement)
                .forEachOrdered(dataStatement -> data.addAll(dataStatement.getNumbers()));

        return new Execution.Data(data);
    }

}
