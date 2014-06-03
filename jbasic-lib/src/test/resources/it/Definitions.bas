10 DEF fun1() = 123
11 LET x1 = fun1()
12 PRINT "fun1() = 123 =", x1

20 DEF fun2() = cos(0) - (-1)
21 LET x2 = fun2()
22 PRINT "fun2() = 2 =", x2

30 DEF neg(x) = -x
31 LET x3 = neg(45)
32 PRINT "neg(45) = -45 =", x3

41 LET x = 100
42 LET y =  23
43 LET x4 = neg(x+y)
44 PRINT "neg(100+23) = -123 =", x4

50 DEF sum(a,b) = a + b
51 LET x5 = sum(100,23)
52 PRINT "sum(100,23) = 123 =", x5

60 DEF sum5(a,b,c,d,e) = a + b + c + d + e
61 LET x6 = sum5(1+9, 10*2, SQR(900), x, -y)
62 PRINT "sum5(1+9, 10*, SQR(900), x, -y) = 137 =" , x6

70 DEF fwd(x) = abs(x) ^ 3 + sum(x,1)
71 LET x7 = fwd(-3)
72 PRINT "fwd(-3) = 25 =", x7
