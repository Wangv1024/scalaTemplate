#### Week 2

##### Multi parameter list:

// sum is a function that returns a function
def sum(f: Int => Int)(a: Int, b: Int): Int =
    if (a > b) 0 else f(a) + sum(f)(a + 1, b)