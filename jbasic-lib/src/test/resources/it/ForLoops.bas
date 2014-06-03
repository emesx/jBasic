10 PRINT "** simple loop"
11 LET x1 = 0
12 FOR i1 = 1 TO 10
13   LET x1 = x1 + i1
14 NEXT i1

20 PRINT "** nested and advanced loops"
21 LET x2 = 1000
22 FOR i = SQR(100)-9 TO SQR(10*10)+0
23   LET x2 = x2 + i*10
24   FOR j = 3*5 TO 1 STEP (-1)*(2-1)
25      LET x2 = x2 + j
26   NEXT j
27   LET x2nested = -1
28 NEXT i

30 PRINT "** gosub in loops"
31 LET x3 = 1
32 FOR i = 1 TO 4
33   GOSUB 80
34   FOR j = 1 TO i
35      GOSUB 85
36   NEXT j
38 NEXT i

40 PRINT "** jumps in loops"
41 LET x4 = 0
42 LET x5 = 0
43 FOR i = 3 TO 5
44   FOR j = i+1 TO i+2
45      LET x4 = 10*i + j
46      IF x4 >= 45 THEN 49
47   NEXT j
48 NEXT i
49 REM -- after loop --

50 PRINT "** loops after jumping out"
51 LET x5 = 0
52 FOR k = 1 TO abs(-3)
53  LET x5 = 1000*k + 100*i + 10*j
54 NEXT k


70 STOP

80 LET x3 = 10*x3
81 RETURN

85 LET x3 = x3+1
86 RETURN

99 END
