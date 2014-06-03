# jBasic

[Dartmouth BASIC](http://en.wikipedia.org/wiki/Dartmouth_BASIC) interpreter written in Java.

The interpreter supports a major subset of the 4th (and thus earlier) edition of the language. The language is described for instance [here](http://www.bitsavers.org/pdf/dartmouth/BASIC_4th_Edition_Jan68.pdf) .

The project consists of the interpreter build as a library and a simple executable application using it.

Developed to celebrate BASIC's [50th birthday](http://www.dartmouth.edu/basicfifty/).

## Requirements
- Maven
- JDK 8

## TODOs
Ideas, things to be done or fixed are pointed out in the source files. They include:

- support for matrices (`MAT`),
- support for arrays (`DIM`),
- parsing scientific notation (eg. `1.2e10`),
- refactoring of expression parser.