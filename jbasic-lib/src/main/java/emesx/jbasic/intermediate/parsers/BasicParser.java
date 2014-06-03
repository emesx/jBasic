package emesx.jbasic.intermediate.parsers;

import emesx.jbasic.frontend.TokenizingIterator;
import emesx.jbasic.frontend.tokens.Number;
import emesx.jbasic.frontend.tokens.Keyword;
import emesx.jbasic.frontend.tokens.LineFeed;
import emesx.jbasic.intermediate.ir.Statement;
import emesx.jbasic.utils.BufferedIterator;
import emesx.jbasic.utils.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static emesx.jbasic.frontend.tokens.Keyword.*;

public class BasicParser extends BufferedIterator<Statement> {
    private static final Logger LOG = LoggerFactory.getLogger(BasicParser.class);

    private final TokenizingIterator iterator;
    private final Map<Keyword, StatementParser> parsers;
    private Optional<Statement> next;

    public BasicParser(TokenizingIterator iterator) {
        this.iterator = iterator;
        this.parsers = new HashMap<>();
        this.next = Optional.empty();

        parsers.put(DATA,   new DataParser());
        parsers.put(DEF,    new DefParser());
        parsers.put(END,    new EndParser());
        parsers.put(FOR,    new ForParser());
        parsers.put(GOSUB,  new GosubParser());
        parsers.put(GOTO,   new GotoParser());
        parsers.put(IF,     new IfParser());
        parsers.put(LET,    new LetParser());
        parsers.put(NEXT,   new NextParser());
        parsers.put(PRINT,  new PrintParser());
        parsers.put(READ,   new ReadParser());
        parsers.put(REM,    new RemParser());
        parsers.put(RETURN, new ReturnParser());
        parsers.put(STOP,   new StopParser());
    }

    @Override
    protected Statement getNext() {
        skipLineFeeds();
        return parseStatement();
    }

    private void skipLineFeeds() {
        while (iterator.hasNext() && iterator.peek() instanceof LineFeed) {
            iterator.next();
        }
    }

    private Statement parseStatement() {
        int lineNumber = ((Number) iterator.next()).getValue().intValue(); // TODO construct dummies here, sort, then parse
        Keyword type = (Keyword) iterator.next();
        StatementParser parser = parsers.get(type);
        Validator.requireNonNull(parser, "Unexpected statement type: " + type);

        Statement statement = parser.parse(iterator);
        statement.setNumber(lineNumber);
        return statement;
    }
}
