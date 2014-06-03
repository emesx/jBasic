package emesx.jbasic.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static emesx.jbasic.utils.Utils.fmt;

public class SymbolTableStack extends SymbolTable {
    private static final Logger LOG = LoggerFactory.getLogger(SymbolTableStack.class);
    private final List<SymbolTable> symbolTables;

    public SymbolTableStack(SymbolTable symbolTable) {
        this.symbolTables = new LinkedList<>();
        symbolTables.add(symbolTable);
    }

    @Override
    public boolean contains(String variable) {
        return find(variable).isPresent();
    }

    @Override
    public Object get(String variable) {
        Optional<SymbolTable> scope = find(variable);
        if (scope.isPresent())
            return scope.get().get(variable);

        throw new RuntimeException(fmt("Variable `%s` not found in any scope", variable));
    }

    @Override
    public void set(String variable, Object value) {
        Optional<SymbolTable> scope = find(variable);

        if (scope.isPresent()) {
            LOG.trace("Updating variable `{}` to value {}", variable, value);
            scope.get().set(variable, value);
        } else {
            LOG.trace("Setting new variable `{}` with value {}", variable, value);
            symbolTables.get(0).set(variable, value);
        }

    }

    public void pushTable(SymbolTable symbolTable) {
        symbolTables.add(0, symbolTable);
    }

    public void pushTable() {
        pushTable(new SymbolTable());
    }

    public SymbolTable popTable() {
        return symbolTables.remove(0);
    }

    private Optional<SymbolTable> find(String variable) {
        return symbolTables.stream().filter(scope -> scope.contains(variable)).findFirst();
    }


}
