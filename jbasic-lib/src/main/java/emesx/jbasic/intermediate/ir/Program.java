package emesx.jbasic.intermediate.ir;

import java.util.List;

public class Program {
    private List<Statement> main;

    public Program(List<Statement> main) {
        this.main = main;
    }

    public List<Statement> getMain() {
        return main;
    }
}
