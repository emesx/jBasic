package emesx.jbasic.intermediate.ir.statements;

import emesx.jbasic.intermediate.ir.Statement;

import java.util.List;

public class DataStatement extends Statement {
    private List<Double> numbers;

    public DataStatement(List<Double> numbers) {
        this.numbers = numbers;
    }

    public List<Double> getNumbers() {
        return numbers;
    }
}
