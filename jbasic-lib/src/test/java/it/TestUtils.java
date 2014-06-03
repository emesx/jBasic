package it;

import emesx.jbasic.backend.Interpreter;
import emesx.jbasic.backend.SymbolTable;
import emesx.jbasic.frontend.Tokenizer;
import emesx.jbasic.intermediate.Parser;
import emesx.jbasic.intermediate.ir.Program;

import java.io.File;
import java.net.URL;

public abstract class TestUtils {

    public static void runProgram(String fileName, SymbolTable symbolTable) {
        Program program = TestUtils.loadProgram(fileName);
        Interpreter interpreter = new Interpreter();
        interpreter.run(program, symbolTable);
    }

    public static Program loadProgram(String fileName) {
        URL url = TestUtils.class.getResource(fileName);
        File file = new File(url.getFile());

        Tokenizer tokenizer = new Tokenizer(file);
        Parser parser = new Parser(tokenizer);
        return parser.parse();
    }
}
