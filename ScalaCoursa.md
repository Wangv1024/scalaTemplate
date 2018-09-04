#### Week 2

##### Multi parameter list:

// sum is a function that returns a function
def sum(f: Int => Int)(a: Int, b: Int): Int =
    if (a > b) 0 else f(a) + sum(f)(a + 1, b)

```
sum(cube)(1, 10) -> sumCube(1, 10) 

// sum(cube) returns function that is sumCube applys to (1, 10)
```

##### Currying
```scala
object exercise {
    def product(f: Int => Int)(): Int =
        if(a > b) 1
        else f(a) * product(f)(a + 1, b)

    product(x => x * x)(3, 4)

    def fact(n: int) = product(x => x)(1, n)
    fact(5)

    def mapReduce(f: Int => Int, combine: (Int, Int) => Int, zero: Int)(a: Int, b: Int): Int =
        if(a > b) zero
        else combine(f(a), mapReduce(f, combine, zero)(a + 1, b))

    // with mapReduce defined
    // define product
    def product(f: Int => Int)(a: Int, b: Int): Int = mapReduce(f, (x, y) => x * y, 1)(a, b)
    // this is the code use mapreduce
}


import math.abs

object exercise{
    val tolerance = 0.0001
    def isCloseEnough(x: Double, y: Double){
        abs(x, y) < tolerance
    }
    def fixedPoint(f: Double => Double)(guess: Double){
        def iterate(guess: Double){
            result: Double = f(guess)
            if(isCloseEnough(result, guess)) return guess
            else return iterate(guess)
        }
        iterate(Double)
    }
}

// Calculate sqrt(2) 
import math.abs
object execise {
    val tolerance = 0.0001
    def isSatisfied(x: Double, y: Double) = 
        abs((x - y) / x) / x < tolerance
    def fixPoint(f: Double => Double)(guess: Double): Double ={
        def iterate(guess:Double):Double = {
            val nextguess = (f(guess) + guess)/2
            if(isSatisfied(nextguess, guess)) nextguess
            else iterate(nextguess)
        }
        iterate(guess)
    }
    def sqrt(x:Double) = fixPoint(y => x/y)(1)
    def main(args: Array[String]): Unit = {
        println(sqrt(2.0))
    }
}
```

#### Context free syntax
EBNF
{} repetition (0 or more)
[] an option
|  denotes an alternative

```
// class define and use
object rational {
    val x = new Rational(1, 2)
    x.numer
    x.demon
}
class Rational(x: Int, y: Int){
    require(y != 0, "denominator must be nonzero") // a test must passed in order to make current class initialized
    // There are also  assert(x >= 0) function

    // Constructor
    def this(x: Int) = this(x, 1)

    private def gcd(a: Int, b: Int): Int =
        if(b == 0) a
        else gcd(b, a % b)
    private val g = gcd(x, y)

    def numer = x / g // Now, we have x,y pre simplified by g
    def demon = y / g
    
//  val numer = x / g // Now, it only calculate once
//  val demon = y / g

    def less(that: Rational) = numer * that.denom < that.numer * denom

    def max(that: Rational) = if(this.less(that)) that else this

    def add(that: Rational) = 
        new Rational(
            numer * that.denom + that.numer * denom,
            denom * that.denom
        )

    def neg: Rational = new Rational(-numer, denom)

    def sub(that: Rational) = add(that.neg) // Don't repeat yourself

    override def toString = numer + "/" + denom
}
```


#### Class substitution, evaluation
new Class(v1, v2, v3, .. vm).f(w1, w2, w3, .. wn)

1. Substitution of parameters y1... by function arguments w1,,,wm
2. substitution of x1,.. xm of class c by v1,...vm
3. substitution of self reference "this" by object new c(v1, v2.. vm)

new Rational(1, 2).numer
-> [1/x, 2/y] [] [new Rationa(1,2)/this] x
= 1

new Rational(1, 2).less(new Rational(2, 3))
-> [1/x, 2/y] [new Rational(2, 3)/that] [new Rational(1,2)/this]
    this.numer * that.denom < that.numer * this.denom

= new Rational(1, 2).numer * new Rational(2, 3).denom < new Rational(2, 3).numer * new Rational(1, 2).denom
= 1 * 3 < 2 * 2
= true



#### Infix Notation
r add s   inplace of r.add(s)


```
    def less(that: Rational) = numer * that.denom < that.numer * denom
->  def < (that: Rational) = numer * that.denom < that.numer * denom

    def max(that: Rational) = if(this.less(that)) that else this


    def unary_- : Rational = new Rational(-numer, denom)
    def - (that: Rational) = this + -that
```

Operation Precedence Rules
(all letters)
|
^
&
<>
= !
:
+-
*/%
(all other special characters)

high priority


a + b ^? c ?^ d less a ==> b | c

((a + b)^?(c ?^))less((a ==> b)|c)


#### abstract
```scala
abstract class IntSet {
    def incl(x: Int): IntSet
    def contains(x: Int): Boolean
}
class Empty extencs IntSet {
    def contains(x: Int): Boolean = false
    def incl(x: Int): IntSet = new NonEmpty(x, new Empty, new Empty)
}
class NonEmpty(elem: Int, left: IntSet, right: IntSet) extends IntSet {
    def contains(x: Int): Boolean = 
        if (x < elem) left contains x
        else if (x > elem) right contains x
        else true
    def incl(x: Int): intSet = 
        if (x < elem) new NonEmpty(elem, left incl x, right)
        else if(x > elem) new NonEmpty(elem, left, right incl x)
        else this
}

// Have singleton object
object Empty extends IntSet {
    def contains(x: Int): Boolean = false
    def incl(x: Int): IntSet = new NonEmpty(x, Empty, Empty)
}
```


#### Implements Union Method
```scala
abstract class IntSet {
  def incl(x: Int): IntSet
  def contains(x: Int): Boolean
  def union(other: IntSet): IntSet
}

class Empty extencs IntSet {
    def contains(x: Int): Boolean = false
    def incl(x: Int): IntSet = new NonEmpty(x, new Empty, new Empty)
    override union(other: IntSet): IntSet = other
}

class NonEmpty(elem: Int, left: IntSet, right: IntSet) extends IntSet {
    def contains(x: Int): Boolean = 
        if (x < elem) left contains x
        else if (x > elem) right contains x
        else true
    def incl(x: Int): intSet = 
        if (x < elem) new NonEmpty(elem, left incl x, right)
        else if(x > elem) new NonEmpty(elem, left, right incl x)
        else this
    override union(other: IntSet): IntSet = {
        other.map{case (oneNode) => other.incl(oneNode.elem)}
        other
    }

// However:
    override union(other: IntSet): IntSet = 
        ((left union right) union other) incl elem
// Because it calls entity at smaller scale, so this recursion must end at somewhere.
}
```

#### Dynamic Binding:
Java scala has dynamic method dispatch.
Code invoked depends on the run-time type of objects.

eg. Empty contains 1 


#### Import:
```scala
import week3._
import week3.rational
import week3.{rational, something}
```

Class, Object, traits can inherit one class but arbitrary traits 
```scala
class Square extends Shape with Planner with Movalble

// traits can not have parameters, only class can.
// traits can contains both implementation or just field
```

#### Exception:
```
object scratch{
    def error(msg :String) = throw new Error(msg) // return type Nothing
}
```

#### Type Parameters:
```scala
trait List[T]
class Cons[T](val head: T, val tail: List[T]) extends List[T]
class Nil[T] extends List[T]

trait List[T]{
    def isEmpty: Boolean
    def head: T
    def tail: List[T]
}

class Cons[T] (val head: Tm, val tail: List[T]) extends List[T]{
    def isEmpty = false

}
class Nil[T] extends List[T] {
    def isEmpty: Boolean = true
    def head: Nothing = throw new NoSuchElementExceptions("Nil.head")
    def tail: Nothing = throw new NoSuchElementExceptions("Nil.tail")
}

def singleton[T](elem: T) = new Cons[T](elem, new Nil[T])
// We can then write:
singleton[Int](1)
singleton[Boolean](true)

//Type erasure: type only for complier to check. Removed before evaluation.  eg: Java Scala, Haskell, ML, OCaml
// keep type parameters aroudn at run time, C++ C#, F#

```

#### Polymorphisum:
subtyping: instances of a subclas can be passed to a base class.
generics: instances of a function or class are created by type parameterization.


```
// Implements nth number, exced it, throw exception

object nth{
    def nth[T](n: Int, xs: List[T]): T = 
        if(xs.isEmpty) throw new IndexOutOfBoundsException
        else if (n == 0) xs.head
        else nth(n - 1, xs.tail)

    val list = new Cons(1, new Con(2, new Cons(3, new Nil)))
}

```