package emesx.jbasic.frontend.tokens;

public class LineFeed extends AbstractToken<String> {
    // TODO singleton
    public LineFeed() {
        super("<LF>");
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof LineFeed;
    }
}
