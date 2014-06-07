# jBasic

[Dartmouth BASIC](http://en.wikipedia.org/wiki/Dartmouth_BASIC) interpreter written in Java.

The interpreter supports a major subset of the 4th (and thus earlier) edition of the language. The language is described for instance [here](http://www.bitsavers.org/pdf/dartmouth/BASIC_4th_Edition_Jan68.pdf) .

The project consists of the interpreter build as a library and a simple executable application using it.

Developed to celebrate BASIC's [50th birthday](http://www.dartmouth.edu/basicfifty/).

## Building and usage
To build the project you need Maven 3 and JDK 8.

Sample usage of the library is best shown in the app itself. To use the interpreter app just type:

    java -jar jbasic.jar your_script.bas
    
You can also specify the logging level via (e.g. `-log=TRACE`) to see exactly what's going on inside.


## TODOs
Ideas, things to be done or fixed are pointed out in the source files. They include:

- support for matrices (`MAT`),
- support for arrays (`DIM`),
- parsing scientific notation (eg. `1.2e10`),
- support for configuring runtime environments, 
- refactoring of expression parser.