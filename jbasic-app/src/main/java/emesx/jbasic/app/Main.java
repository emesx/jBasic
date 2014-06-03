package emesx.jbasic.app;

import ch.qos.logback.classic.Level;
import emesx.jbasic.backend.Interpreter;
import emesx.jbasic.frontend.Tokenizer;
import emesx.jbasic.intermediate.Parser;
import emesx.jbasic.intermediate.ir.Program;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Typing is no substitute for thinking
 */
public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Optional<Options> opts = Options.parse(args);

        if (!opts.isPresent())
            return;

        setLogging(opts.get());
        run(opts.get());
    }

    private static void setLogging(Options opts) {
        Level level = Level.INFO;

        try {
            level = Level.valueOf(opts.logLevel.toUpperCase());
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(level);
    }

    private static void run(Options opts) {
        LOG.debug("Parsing `{}`", opts.file.getAbsolutePath());

        Tokenizer tokenizer = new Tokenizer(opts.file);
        Parser parser = new Parser(tokenizer);
        Program program = parser.parse();
        Interpreter interpreter = new Interpreter();

        LOG.debug("Running...");
        interpreter.run(program);
        LOG.debug("Finished.");
    }

}
