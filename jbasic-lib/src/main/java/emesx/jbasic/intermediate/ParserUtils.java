package emesx.jbasic.intermediate;

import emesx.jbasic.frontend.TokenizingIterator;
import emesx.jbasic.frontend.tokens.Token;
import emesx.jbasic.intermediate.ir.Statement;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static emesx.jbasic.utils.Utils.fmt;

public abstract class ParserUtils {

    public static void consume(TokenizingIterator iterator, Token<?> expected) {
        Token<?> actual = iterator.next();
        if (!actual.equals(expected))
            throw new RuntimeException(fmt("Expecting %s but found %s", expected, actual));
    }

    public static boolean instanceOfAny(Object object, Class<?>... classes) {
        return classes != null
                && classes.length > 0
                && Arrays.stream(classes).anyMatch(clazz -> clazz.isInstance(object));
    }

    public static boolean equalsAny(Object object, Object... objects) {
        return object != null
                && Arrays.stream(objects).anyMatch(object::equals);

    }

    public static void sortLinkStatements(List<Statement> statements) {
        Collections.sort(statements, Comparator.comparing(Statement::getNumber));

        Statement current = statements.get(0);
        current.getFirst().setPrevious(null);

        for (int i = 1; i < statements.size(); ++i) {
            Statement next = statements.get(i);
            linkStatements(current.getLast(), next.getFirst());
            current = next;
        }

        current.getLast().setNext(null);
    }

    public static void linkStatements(Statement first, Statement second) {
        first.setNext(second);
        second.setPrevious(first);
    }
}
