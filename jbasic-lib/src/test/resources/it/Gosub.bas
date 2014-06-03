10 LET X = 0
11 LET Y = 0
12 LET I = 1

20 IF I > 5 THEN 99
21  PRINT "Iteration", INT(I)
22  GOSUB 50
23  GOSUB 70
24  LET I = inc(I)
25  PRINT ""
26 GOTO 20


50 REM increase x and decrease y -----------------------------------------------
51  LET X = inc(X)
52  LET Y = Y - 1
53 RETURN


60 REM print x and y -----------------------------------------------------------
61  PRINT "X =", X
62  PRINT "Y =", Y
63 RETURN


70 REM increase and print all --------------------------------------------------
71  GOSUB 50
72  GOSUB 60
73 RETURN


90 DEF inc(i) = i+1

99 END
