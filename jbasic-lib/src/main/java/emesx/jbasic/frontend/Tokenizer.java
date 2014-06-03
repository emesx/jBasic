package emesx.jbasic.frontend;

import emesx.jbasic.frontend.tokens.Token;
import emesx.jbasic.utils.Validator;

import java.io.File;

public class Tokenizer implements Iterable<Token<?>> {
    private final File file;

    public Tokenizer(File file) {
        Validator.require(file::exists, "File does not exist");
        this.file = file;
    }

    @Override
    public TokenizingIterator iterator() {
        return new TokenizingIterator(file);
    }

}
