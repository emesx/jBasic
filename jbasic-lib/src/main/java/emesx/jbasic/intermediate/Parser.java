package emesx.jbasic.intermediate;

import emesx.jbasic.frontend.Tokenizer;
import emesx.jbasic.intermediate.ir.Program;
import emesx.jbasic.intermediate.ir.Statement;
import emesx.jbasic.intermediate.parsers.BasicParser;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final BasicParser basicParser;

    public Parser(Tokenizer tokenizer) {
        basicParser = new BasicParser(tokenizer.iterator());
    }

    public Program parse() {
        List<Statement> main = new ArrayList<>();

        while (basicParser.hasNext()) {
            main.add(basicParser.next());
        }

        ParserUtils.sortLinkStatements(main);
        return new Program(main);
    }

}
