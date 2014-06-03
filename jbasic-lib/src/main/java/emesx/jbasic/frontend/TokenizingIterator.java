package emesx.jbasic.frontend;

import emesx.jbasic.frontend.tokens.*;
import emesx.jbasic.frontend.tokens.Number;
import emesx.jbasic.utils.BufferedIterator;
import emesx.jbasic.utils.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static emesx.jbasic.utils.Utils.fmt;


public class TokenizingIterator extends BufferedIterator<Token<?>> {
    private static final Logger LOG = LoggerFactory.getLogger(TokenizingIterator.class);

    private CharReader reader;

    public TokenizingIterator(File file) {
        Validator.require(file::exists, "File does not exist");
        this.reader = new CharReader(file);
    }


    @Override
    protected Token<?> getNext() {
        skipWhitespace();

        char peek = reader.peek();

        if (peek == '\n')
            return newLineFeed();

        if (Character.isLetter(peek))
            return nextIdentifier();

        if (Operator.STRING_DELIMITER.startsWith(peek))
            return nextStringLiteral();

        if (Operator.mayStartWith(peek))
            return nextOperator();

        if (Character.isDigit(peek))
            return nextNumber();

        //TODO return a <EOF> token maybe
        throw new RuntimeException(fmt("Unexpected token `%s`", peek));
    }

    private void skipWhitespace() {
        while (reader.hasNext() && Character.isWhitespace(reader.peek()) && reader.peek() != '\n')
            reader.next();
    }

    private Token<?> newLineFeed() {
        reader.next();
        return new LineFeed();
    }

    private StringLiteral nextStringLiteral() {
        StringBuilder builder = new StringBuilder();
        reader.next();

        final char END = Operator.STRING_DELIMITER.getString().charAt(0);
        while (reader.peek() != END) {
            if (reader.peek() == '\n')
                throw new RuntimeException("String literal not closed");
            builder.append(reader.next());
        }

        reader.next();
        return new StringLiteral(builder.toString());
    }

    private Token<?> nextIdentifier() {
        StringBuilder builder = new StringBuilder();

        while (Character.isLetterOrDigit(reader.peek()))
            builder.append(reader.next());

        try {
            return Keyword.valueOf(builder.toString().toUpperCase());
        } catch (IllegalArgumentException ignored) {
        }

        return new Identifier(builder.toString());
    }

    private Number nextNumber() {
        StringBuilder builder = new StringBuilder();

        while (Character.isDigit(reader.peek())) // TODO handle e notation i.e. 1.123e45
            builder.append(reader.next());

        if (reader.peek() == '.') {
            builder.append(reader.next()); // dot

            if (!Character.isDigit(reader.peek()))
                throw new RuntimeException("Invalid number format: no digits after decimal point");

            do {
                builder.append(reader.next());
            } while (Character.isDigit(reader.peek()));

            return new Number(Double.valueOf(builder.toString()));
        }

        return new Number(Integer.valueOf(builder.toString()));


    }

    private Operator nextOperator() {
        StringBuilder builder = new StringBuilder();

        while (Operator.mayStartWith(builder.toString() + reader.peek()))
            builder.append(reader.next());

        return Operator.fromString(builder.toString());
    }
}
