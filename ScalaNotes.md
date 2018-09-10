# Essen􏰜al Scala

More resource h􏰝p://underscore.io/training

* every expression in Scala has a type and a value. 
    * The type is determined at compile 􏰜me 
    * and the value is determined by execution the expression.


paste command
* type :paste, press Enter, and write (or copy-and-paste) our code. When we’re done we press Ctrl+D to compile and execute the code as normal.

```
scala> :paste example.scala
Pasting file example.scala...
res0: Int = 6
```

```
 scala> :type println("Hello world!")
Unit
```
Hello World
```
object HelloWorld {
  def main(args: Array[String]): Unit = {
    println("Hello world!")
  }
}
```

Every value in Scala is an object we can also call methods on primi􏰜ve types such as Int and Boolean.


**Infix Operator Nota􏰜on**
- Any Scala expression wri􏰝en a.b(c) can also be wri􏰝en a b c.
Note that a b c d e is equivalent to a.b(c).d(e), not a.b(c, d, e).


#### String
```
"this is a string"
// res: java.lang.String = this is a string
"the\nusual\tescape characters apply"
// res: java.lang.String =
// the
// usual escape characters apply
```

We can declare an empty object as follows:
```
object Test {}
Test
// res: Test.type = Test$@1668bd43
```
Once we have bound the name Test we can use it in expressions, where it evaluates to the object we have declared. 
This is not like any type we’ve seen before—**it’s a new type,**

#### Declare a method
```
 def name(parameter: type, ...): resultType =
  bodyExpression
 def name: resultType =
  bodyExpression
```
>Return is Implicit:
The return value of the method is determined by evalua􏰜ng the body—there is no need to write return like you would in Java.

#### Fields vs Methods
a field gives a name to a value, whereas a method gives a name to a computa􏰜on that produces a value.
```
object Test7 {
    val simpleField = {
     println("Evaluating simpleField")
     42 
    }
    def noParameterMethod = {
     println("Evaluating noParameterMethod")
     42
    } 
}
```

Objects and classes aren’t loaded un􏰜til they are **referenced by other code**. 

Let’s force Scala to evaluate our object body by referencing Test7 in an expression
```
Test7
// Evaluating simpleField
// res: Test7.type = Test7$@b22e8c9
```
When the object is first loaded, Scala runs through its defini􏰜ons and calculates the values of each of its fields. This results in the code prin􏰜ng "Evaluating simpleField" as a side-effect.

The body expression of a field is **run only once** aft􏰞er which the final value is stored in the object. The expression is never evaluated again—no􏰜ce the lack of println output below.

The body of a method, on the other hand, is evaluated **every 􏰜time** we call the method—noti􏰜ce the repreated println output below.


A block is a sequence of expressions or declara􏰜ons surrounded by braces. A block is also an expression: it
executes each of its sub-expressions in order and returns the value of the last expression.

### Constructor
```
class Person(first: String, last: String) {
  val firstName = first
  val lastName = last
  def name = firstName + " " + lastName
}

val dave = new Person("Dave", "Gurnell")
// dave: Person = Person@3ed12df7
dave.name
// res: String = Dave Gurnell

// Practice 2:
class Person(val firstName: String, val lastName: String) { 
    def name = firstName + " " + lastName
}
new Person("Dave", "Gurnell").firstName
// res: String = Dave
```

```
def greet(title: String = "Citizen", firstName: String = "Some", lastName: String = "Body") = "Greetings, " + title + " " + firstName + " " + lastName + "!"

greet("Awesome") // this is now incorrect
// res: String = Greetings, Awesome Some Body

greet(firstName = "Awesome") // this is still correct
// res: String = Greetings, Citizen Awesome Body
```

```
class Adder(amount: Int) {
  def add(in: Int): Int = in + amount
}
```

The apply method
```
class Adder(amount: Int) {
  def apply(in: Int): Int = in + amount
}
val add3 = new Adder(3)
// add3: Adder = Adder@1d4f0fb4
add3(2) // shorthand for add3.apply(2)
// res: Int = 5
```
Auxiliary constructors
```
class Timestamp(val seconds: Long)

object Timestamp {
  def apply(hours: Int, minutes: Int, seconds: Int): Timestamp = new Timestamp(hours*60*60 + minutes*60 + seconds)
}
Timestamp(1, 1, 1).seconds
// res: Long = 3661

Timestamp // note that the type is `Timestamp.type`, not `Timestamp` 
// res: Timestamp.type = Timestamp$@602b24e6
// It is a singleton object with its own type
```
Companion objects must be defined in the __same compila􏰀on unit__ as the classes they support.


Companion Object Syntax
To define a companion object for a class, in the same file as the class define an object with the same name.
```
class Name {
  ...
}
object Name {
  ...
}
```



#### Case class
Case classes are an excep􏰀onally useful shorthand for defining a class, a companion object, and a lot of sensible defaults in one go. They are ideal for crea􏰀ng lightweight data-holding classes with the minimum of hassle.
```
case class Person(firstName: String, lastName: String) { 
  def name = firstName + " " + lastName
}
val dave = new Person("Dave", "Gurnell") 
// we have a class
// dave: Person = Person(Dave,Gurnell)
Person 
// and a companion object too
// res: Person.type = Person
```

Features of case class
* A field for each constructor argument—we don’t even need to write val in our constructor defini􏰀on.
* A default toString method that prints a sensible constructor-like representa􏰀tion of the class
* Sensible equals, and hashCode methods that operate on the field values in the object.

```
new Person("Noel", "Welsh") == new Person("Noel", "Welsh") 
// res: Boolean = true

//Copy method create a new object 
 dave.copy() eq res0
// res: Boolean = false
```


##### object equal
 dave.copy() eq res0

##### copy method accepts partial result
```
dave.copy(firstName = "Dave2")
// res: Person = Person(Dave2,Gurnell)
dave.copy(lastName = "Gurnell2")
// res: Person = Person(Dave,Gurnell2)
```


#### Case objects
If you find yourself defining a case class with no constructor arguments you can instead a define a case object. A case object is defined just like a case class and has the same default methods as a case class.
```
case object Citizen {
  def firstName = "John"
  def lastName  = "Doe"
  def name = firstName + " " + lastName
}
```

* The case object keyword defines a class and an object,and makes the object an instance(actually the only instance) of the class:
* With a case object will sti􏰀ll get all of the func􏰀onality defined for case classes above.
```
class Citizen { /* ... */ }
object Citizen extends Citizen { /* ... */ }
```

#### Pattern Matching:
```
object Stormtrooper {
  def inspect(person: Person): String =
  person match {
    case Person("Luke", "Skywalker") => "Stop, rebel scum!" 
    case Person("Han", "Solo") => "Stop, rebel scum!"
    case Person(first, last) => s"Move along, $first"
  } 
}

Stormtrooper.inspect(Person("Noel", "Welsh")) 
// res: String = Move along, Noel
```

An underscore (_), __which matches any value and ignores it__. For example, as Stormtroopers only care about the first name of ordinary ci􏰀zens we could just write Person(first, _) to __avoid binding a name to the value of the lastName__.


## Charpter4 Modeling Data With Trait
Traits allow us to express that two or more classes can be considered the same, and thus both implement the __same opera􏰀tions__. In other words, traits allow us to express that mul􏰀ple classes share a common super-type.


Example of use trait
```
import java.util.Date
trait Visitor {
  def id: String // Unique id assigned to each user
  def createdAt: Date // Date this user first visited the site
  
  // How long has this visitor been around?
  def age: Long = new Date().getTime - createdAt.getTime
}
case class Anonymous(id: String, createdAt: Date = new Date()) extends Visitor

case class User(
  id: String,
  email: String,
  createdAt: Date = new Date()
) extends Visitor
```

Using Trait:
```
scala> def older(v1: Visitor, v2: Visitor): Boolean =
         v1.createdAt.before(v2.createdAt)
scala> older(Anonymous("1"), User("2", "test@example.com")) older(Anonymous("1"), User("2", "test@example.com"))
res4: Boolean = true
```

Traits vs class

1. Traits can define abstract methods that have names and type signatures but no implementa􏰀on.
2. A trait cannot have a constructor

Id and createdAt are abstract so they must be defined in extending classes.
__Our classes implement them as vals rather than defs__. This is legal in Scala, which sees def as a more general version of val1. It is good prac􏰀ce to __never define vals in a trait__, but rather to use def.


We can still extend User and anonymous unless we mark User and Anonymous as final or seal to disallow it.
```
sealed trait Visitor { /* ... */ }
final case class User(/* ... */) extends Visitor
final case class Anonymous(/* ... */) extends Visitor
```


#####  Modelling Data with Traits
  1. Product Type Pa􏰂ern
    If A has ab(with typeB) and ac(with typeC) write 
    
    case class A(b: B, c: C)

    or

    trait A {
      def b: B
      def c: C
    }

  2. The Sum Type Pa􏰂ttern

    If A is a B or C write

    sealed trait A
    final case class B() extends A
    final case class C() extends A

Sum type with and property:
  trait B
  trait C
  trait A extends B with C


Product Type with or relation:
```
trait A {
  def d: D
}
sealed trait D
final case class B() extends D
final case class C() extends D
```

#### Structural Recursion using Polymorphism
```
sealed trait A {
  def foo: String
}
final case class B() extends A {
  def foo: String =
    "It's B!"
}
final case class C() extends A {
  def foo: String =
    "It's C!"
}
scala> val anA: A = B()
anA: A = B()
scala> anA.foo
res1: String = It's B!
```

Override
```
sealed trait A {
  def foo: String = "It's A!"
}
final case class B() extends A {
  override def foo: String =
    "It's B!"
}
final case class C() extends A {
  override def foo: String =
    "It's C!"
}
```

The Product Type Polymorphism Pa􏰂ttern
```
case class A(b: B, c: C) {
  def f: F = ???
}
```
The Sum Type Polymorphism Patt􏰂ern
```
sealed trait A {
  def f: F
}
final case class B() extends A {
  def f: F = ???
}
final case class C() extends A {
  def f: F = ???
}
```

#### The Product Type Pa􏰂ttern Matching Pa􏰂ttern
```
//If A has ab(with typeB) and ac(with typeC), and we want to write a method of that accepts an A and returns an F, write:

def f(a: A): F =
  a match {
    case A(b, c) => ???
  }

//If A is a B or C, and we want to write a method f accep􏰀ng an A and returning an F,   
def f(a: A): F =
  a match {
    case B() => ???
    case C() => ???
  }
```

```
sealed trait Feline {
  def dinner: Food =
    this match {
      case Lion() => Antelope
      case Tiger() => TigerFood
      case Panther() => Licorice
      case Cat(favouriteFood) => CatFood(favouriteFood)
  } 
}
object Diner {
  def dinner(feline: Feline): Food =
    feline match {
      case Lion() => Antelope
      case Tiger() => TigerFood
      case Panther() => Licorice
      case Cat(food) => CatFood(food)
  }
}
```

#### Where to implement
The general rule is: if a method only depends on other fields and methods in a class it is a good __candidate to be implemented inside the class__. If the method depends on other data (for example, if we needed a Cook to make dinner) consider implementing is __using pa􏰂ttern matching outside of the classes in questi􏰀on__. If we want to have __more than one implementati􏰀on we should use patt􏰂ern matching and implement it outside the classes__.

      Add new method         |     Add new data
  OO Change existi􏰀ng code    |    Existi􏰀ng code unchanged
  FP Existi􏰀ng code unchanged |     Change existi􏰀ng code

One advantage of func􏰀onal style is it allows the compiler to help us more. By sealing traits we are telling the compiler it knows all the possible subtypes of that trait. __It can then tell us if we miss out a case in our patt􏰂ern matching__. This is especially useful if we add or remove subtypes later in development. We could argue we get the same benefit from object-oriented style, as we must implement all methods defined on the base trait in any subtypes. __This is true, but in prac􏰀ce classes with a large number of methods are very difficult to maintain and we’ll inevitably end up factoring some of the code into different classes__ – essen􏰀ally duplica􏰀ting the func􏰀onal style.


```
// polymorphism
sealed trait TrafficLight {
  def next: TrafficLight
}
final case object Red extends TrafficLight {
  def next: TrafficLight =
    Green
}
final case object Green extends TrafficLight {
  def next: TrafficLight =
    Yellow
}
final case object Yellow extends TrafficLight {
  def next: TrafficLight =
    Red
}

// patt􏰂ern matching
sealed trait TrafficLight {
  def next: TrafficLight =
    this match {
      case Red => Green
      case Green => Yellow
      case Yellow => Red
    }
}
final case object Red extends TrafficLight
final case object Green extends TrafficLight
final case object Yellow extends TrafficLight
```



Define operation:
```
assert(Calculator.+(Success(1), 1) == Success(2)) 
assert(Calculator.-(Success(1), 1) == Success(0)) 
assert(Calculator.+(Failure("Badness"), 1) == Failure("Badness"))


// Implementation:
sealed trait Calculation
final case class Success(result: Int) extends Calculation 
final case class Failure(reason: String) extends Calculation

object Calculator {
  def +(calc: Calculation, operand: Int): Calculation =
    calc match {
        case Success(result) => Success(result + operand)
        case Failure(reason) => Failure(reason)
    }
  def -(calc: Calculation, operand: Int): Calculation =
    calc match {
      case Success(result) => Success(result - operand)
      case Failure(reason) => Failure(reason)
    } 
  def /(calc: Calculation, operand: Int): Calculation =
    calc match {
      case Success(result) =>
        operand match {
          case 0 => Failure("Division by zero")
          case _ => Success(result / operand)
        }
      case Failure(reason) => Failure(reason)
    }
}
```


#### Recursive Structural LinkedList
```
sealed trait IntList
final case object End extends IntList
final case class Pair(head: Int, tail: IntList) extends IntList

Pair(1, Pair(2, Pair(3, End)))

val d = End()
val c = Pair(3, d)
val b = Pair(2, c)
val a = Pair(1, b)

// Summation function
def sum(list: IntList): Int =
  list match {
    case End => 0
    case Pair(hd, tl) => hd + sum(tl)
  }
```

##### 4.6.2 Tail Recursion
```
scala> @tailrec
       def sum(list: IntList, total: Int = 0): Int =
         list match {
           case End => total
           case Pair(hd, tl) => sum(tl, total + hd)
         }
sum: (list: IntList, total: Int)Int
```
Any non-tail recursion func􏰀on can be transformed into a tail recursive version by adding an accumulator as we have done with sum above. This transforms stack alloca􏰀on into heap alloca􏰀on, which some􏰀times is a win, and other 􏰀times is not.
In Scala __we tend not to work directly with tail recursive func􏰀ons as there is a rich collec􏰀ons library that covers the most common cases where tail recursion is used__. Should you need to go beyond this, because you’re implemen􏰀ng your own datatypes or are op􏰀mising code, it is useful to know about tail recursion.


Pattern matching 
JSON Part 3
```
sealed trait Json {
  def print: String = {
    def quote(s: String): String =
      '"'.toString ++ s ++ '"'.toString
    def seqToJson(seq: SeqCell): String =
      seq match {
        case SeqCell(h, t @ SeqCell(_, _)) =>
          s"${h.print}, ${seqToJson(t)}"
        case SeqCell(h, SeqEnd) => h.print
      }
    def objectToJson(obj: ObjectCell): String =
      obj match {
        case ObjectCell(k, v, t @ ObjectCell(_, _, _)) => s"${quote(k)}: ${v.print}, ${objectToJson(t)}"
        case ObjectCell(k, v, ObjectEnd) =>
          s"${quote(k)}: ${v.print}"
      }
    this match {
      case JsNumber(v) => v.toString
      case JsString(v) => quote(v)
      case JsBoolean(v) => v.toString
      case JsNull => "null"
      case s @ SeqCell(_, _) => "[" ++ seqToJson(s) ++ "]"
      case SeqEnd => "[]"
      case o @ ObjectCell(_, _, _) => "{" ++ objectToJson(o) ++ "}" case ObjectEnd => "{}"
    } 
  }
}
final case class JsNumber(value: Double) extends Json
final case class JsString(value: String) extends Json
final case class JsBoolean(value: Boolean) extends Json
final case object JsNull extends Json
sealed trait JsSequence extends Json
final case class SeqCell(head: Json, tail: JsSequence) extends JsSequence
final case object SeqEnd extends JsSequence
sealed trait JsObject extends Json
final case class ObjectCell(key: String, value: Json, tail: JsObject) extends JsObject final case object ObjectEnd extends JsObject
```

Type extend
```
sealed trait Result[A]
case class Success[A](result: A) extends Result[A] case class Failure[A](reason: String) extends Result[A]
```

#### Function Place holder Syntax
```
((_: Int) * 2)
// res: Int => Int = <function1>

_+_ //expandsto`(a,b)=>a+b` 
foo(_) // expands to `(a) => foo(a)` 
foo(_, b) // expands to `(a) => foo(a, b)` 
_(foo) // expands to `(a) => a(foo)` // and so on...
```

#### 5.3.3 Conver􏰀ng methods to func􏰀tion. Method to function
```
object Sum {
  def sum(x: Int, y: Int) = x + y
}
(Sum.sum _)
// res: (Int, Int) => Int = <function2>
```

```
class Counter(val count: Int) {
  def dec = new Counter(count - 1)
  def inc = new Counter(count + 1)
  def adjust(adder: Adder) =
    new Counter(adder.add(count))
}

object MathStuff {
  def add1(num: Int) = num + 1
}
Counter(2).adjust(MathStuff.add1)
// res: Counter = Counter(3)
```

#### Fold Pattern
```
def abstraction(end: Int, f: ???): Int =
  this match {
    case End => end
    case Pair(hd, tl) => f(hd, tl.abstraction(f, end))
  }

sealed trait IntList {
  def length: Int =
    this match {
      case End => 0
      case Pair(hd, tl) => 1 + tl.length
    }
  def double: IntList =
    this match {
      case End => End
      case Pair(hd, tl) => Pair(hd * 2, tl.double)
    }
  def product: Int =
    this match {
      case End => 1
      case Pair(hd, tl) => hd * tl.product
    }
  def sum: Int =
    this match {
      case End => 0
      case Pair(hd, tl) => hd + tl.sum
  } 
}
```



#### Parameter List
```
def fold[B](end: B)(pair: (A, B) => B): B =
  this match {
    case End() => end
    case Pair(hd, tl) => pair(hd, tl.fold(end, pair))
  }

//then we can call it as
 fold(0){ (total, elt) => total + elt }  

```

```
def fold[B](end: B, pair: (A, B) => B): B
```
if we define it this way.
if Scala infers B for end it cannot then use this inferred type for the B inpair, so we must o􏰅pen write a type declarati􏰀on on pair. However, __Scala can use types inferred for one parameter list in another parameter list__. So if we write fold as
```
 def fold[B](end: B)(pair: (A, B) => B): B
```
then inferring B from end (which is usually easy) __allows B to be used when inferring the type pair__. 


#### 5.4.1 Generic Product Types
```
// Pair:
def intAndString: Pair[Int, String] = // ...
def booleanAndDouble: Pair[Boolean, Double] = // ...
```

#### Tuple:
```
val x = (1, "b", true)
// x: (Int, String, Boolean) = (1,b,true)
x._1
// res: Int = 1
x._3
// res: Boolean = true
```

#### Generic Sum Type
```
Left[Int, String](1).value
// res: Int = 1
Right[Int, String]("foo").value
// res: String = foo
val sum: Sum[Int, String] = Right("foo")

// sum: Sum[Int,String] = Right(foo)
sum match {
  case Left(x) => x.toString
  case Right(x) => x
}
// res: String = foo

sealed trait Sum[A, B]
final case class Left[A, B](value: A) extends Sum[A, B]
final case class Right[A, B](value: B) extends Sum[A, B]
```

Sum Type fold
```
sealed trait Sum[A, B] {
  def fold[C](left: A => C, right: B => C): C =
    this match {
      case Left(a) => left(a)
      case Right(b) => right(b)
    } 
}
final case class Left[A, B](value: A) extends Sum[A, B]
final case class Right[A, B](value: B) extends Sum[A, B]
```


#### 5.4.4 Generic Op􏰀onal Values
One common property is “correctly handle errors”. If we can encode an op􏰁onal value in the type system, the compiler will force us to consider the case where a value is not available, thus increasing the robustness of our code.

```
val perhaps: Maybe[Int] = Empty[Int]
// perhaps: Maybe[Int] = Empty()
val perhaps: Maybe[Int] = Full(1)
// perhaps: Maybe[Int] = Full(1)

sealed trait Maybe[A]
final case class Full[A](value: A) extends Maybe[A]
final case class Empty[A]() extends Maybe[A]
```

These abstrac􏰀ons are commonly used in Scala code and have implementa􏰀ons in the Scala standard library.
__The sum type is called Either, products are tuples, and op􏰀onal values are modelled with Option.__




#### Map function using fold pattern

```
sealed trait LinkedList[A] {
  def map[B](fn: A => B): LinkedList[B] =
    this match {
      case Pair(hd, tl) => Pair(fn(hd), tl.map(fn))
      case End() => End[B]()
    } 
}
```


#### Parallel Iteration
```
for {
  x <- Seq(1, 2, 3)
  y <- Seq(4, 5, 6)
} yield x + y
// Seq[Int] = List(5, 6, 7, 6, 7, 8, 7, 8, 9)

Seq(1, 2, 3).zip(Seq(4, 5, 6))
// res: Seq[(Int, Int)] = List((1, 4), (2, 5), (3, 6))


for{
  x <- Seq(1, 2, 3)
  square = x * x
  y <- Seq(4, 5, 6)
} yield square * y
// res: Seq[Int] = List(4, 5, 6, 16, 20 ,24, 36, 45, 54)
```

#### Maps and Sets
```
val example = Map("a" -> 1, "b" -> 2, "c" -> 3)

example("a") 
// res: Int = 1

example.get("a")
// res: Option[Int] = Some(1)

example("d")
// java.util.NoSuchElementException: key not found: d
exmaple.get("d")
// res: Opetion[Int] = None

example.getOrElse("d", -1) 
// res: Int = -1

example.+("c" -> 10, "d" -> 11, "e" -> 12)
example.-("b", "c")
```

```
// mutable.Map("x" -> 10, "y" -> 11, "z" -> 12)
// mutable Map will return the same object

val example2 = scala.collection.mutable.Map("x" -> 10, "y" -> 11, "z" -> 12)

example2 += ("x" -> 20)
// res: example2.type = Map(x -> 20, z -> 12, y -> 11)

example2 -= ("y", "z")
// res: example2.type = Map(x -> 20)

example2("w") = 30 // update 

// ListMap: Map reservce relative order
scala.collections.immutable.ListMap("a" -> 1) + ("b" -> 2) + ("c" -> 3)

// Map(a -> 1, b -> 2, c -> 3, d -> 4) 

```

#### Scala performance
https://docs.scala-lang.org/overviews/collections/performance-characteristics.html

#### Map & flatMap
```scala
example.flatMap {
        case (str, num) =>
          (1 to 3).map(x =. (str + x) -> (num * x))
        }
// Or:

for {
  (str, num) <- example
  x <- 1 to 3
} yield (str + x) -> (num * x)

// Map(c2 -. 9, b2 -> 4, b3 -> 6, c2 -> 6,..)
```

#### Range

```scala
1 until 10

10 until 1 by -1
// 10, 9,...3, 2

for(i <- 99 until 0 by -1) println(i + " bottles of beer on the wall!")

(1 until 10) ++ (20 until 30)
// res: scala.collection.immutable.Indexedseq[Int] = Vector(1, 2, .9, 20, .., 29)
```




#### Implicits:
Implicites found in the local scope take precedence over those found in companion objects.


#### Type Class Pattern
```scala
trait HtmlWriter[A] {
  def write(in: A): String
}
object PersonWriter extencs HtmlWriter[Person] {
  def write(person: Person) = s"<span>${person.name} &lt;{person.email}&gt;</span>"
}

object DateWriter extends HtmlWriter[Date] {
  def write(in: Date) = s"<span>$in.toString</span>"
}
```

```scala
object HtmlUtil {
  def htmlify[A](data: A)(implicit writer: HtmlWriter[A]): String = {
    writer.write(data)
  }
}

// In usage:
HtmlUtil.htmlify(Person("John", "john@example.com"))(PersonWriter)
// res: String = <span>John &lt;john@example.com&gt;</span>

// If we omit the implicit in the parameter list, then
implicit object ApproximationWriter extends HtmlWriter[Int] {
  def wirte(in: Int): String = 
    s"It's definitely less than ${((in / 10) + 1) * 10}"
}
```


#### Type Class Interface Pattern
```scala
object Htmlwriter {
  def apply[A](implicit writer: HtmlWriter[A]): HtmlWriter[A] = 
    writer
}

HtmlWriter[Person].write(Person("Noel", "noel@example.org"))

// 7.4.2
object TypeClass {
  def apply[A](implicit instance: TypeClass[A]): TypeClass[A] = 
    instance
}
```

```scala
class ExtraStringMethods(str: String) {
  val vowels = Seq('a', 'e', 'i', 'o', 'u')
  
  def numberOfVowels = 
    str.toList.fileter(vowels contains _).length
}
// implicit applies to all the parameters

implicite clas HtmlOps[T](data: T) {
    def toHtml(implicit writer: HtmlWriter[T]) = 
      writer.write(data)
}
// User Type as parameter to choose function

// In useage
Person("John", "john@example.com").toHtml
// res: String = <span>John@example.com&gt;</span>

// 7.7.1
// Context Bounds
def pageTmplate[A : Htmlwriter](body: A) : String = {
  val renderedBody = body.toHtml

  s"<html><body>${renderedBody}</body></html>"
}
```

#### Implicitly
```scala
case class Example(name: String)

implicit val implicitExample = Example("implicit")

implicitly[Example]
// res: Example = Example(implicit)
```


Where to place implicit
1. byplacingtheminanobjectsuchasJsonWriterInstances; 
2. byplacingtheminatrait;
3. byplacingtheminthecompanionobjectofthetypeclass;
4. byplacingtheminthecompanionobjectoftheparametertype.


#### 
```
implicit def optionWriter[A]
(implicit writer: JsonWriter[A]): JsonWriter[Option[A]] =
  new JsonWriter[Option[A]] {
    def write(option: Option[A]): Json =
      option match {
        case Some(aValue) => writer.write(aValue)
        case None         => JsNull
      }
  }
```

```
 Json.toJson(Option("A string"))
```
it searches for an implicit JsonWriter[Option[String]]. It finds the im- plicit method for JsonWriter[Option[A]]:

