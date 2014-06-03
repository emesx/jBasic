package emesx.jbasic.backend;

import emesx.jbasic.utils.Validator;

import java.util.HashMap;
import java.util.Map;

import static emesx.jbasic.utils.Utils.fmt;

public class SymbolTable {
    private final Map<String, Object> symbols;

    public SymbolTable() {
        this.symbols = new HashMap<>();
    }

    public boolean contains(String variable) {
        Validator.requireNonNull(variable, "Variable name cannot be null");
        return symbols.containsKey(variable.toUpperCase());
    }

    public Object get(String variable) {
        if (contains(variable))
            return symbols.get(variable.toUpperCase());

        throw new RuntimeException(fmt("Variable `%s` not found in scope", variable));
    }

    public void set(String variable, Object value) {
        Validator.requireNonNull(variable, "Variable name cannot be null");
        symbols.put(variable.toUpperCase(), value);
    }
}
