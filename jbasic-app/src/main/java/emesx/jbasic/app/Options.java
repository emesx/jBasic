package emesx.jbasic.app;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.util.Optional;

public class Options {
    @Argument(required = true, metaVar = "FILE", usage = "Path to script file")
    public File file;

    @Option(name = "-log", metaVar = "LEVEL", usage = "Logging level (default INFO)")
    public String logLevel = "INFO";


    public static Optional<Options> parse(String[] args) {
        Options options = new Options();
        CmdLineParser parser = new CmdLineParser(options);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println("jBasic: a Dartmouth BASIC interpreter.\nUsage:");
            parser.printUsage(System.err);
            return Optional.empty();
        }
        return Optional.of(options);
    }

}
