package emesx.jbasic.frontend;

import emesx.jbasic.utils.BufferedIterator;

import java.io.*;

public class CharReader extends BufferedIterator<Character> {
    private Reader reader;

    public CharReader(File file) {
        try {
            this.reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    protected Character getNext() {
        int nextChar;
        try {
            if ((nextChar = reader.read()) >= 0) {
                return (char) nextChar;
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        throw new IllegalStateException("Reader empty");
    }

}