/*
object currying {
	def intervalProduct(f: Int => Int)(a:Int, b:Int):Int = {
		if(a > b) 
			return 1
		else{
			return f(a) * intervalProduct(f)(a + 1, b)
		}
	}
	def main(args: Array[String]): Unit = {
		println(intervalProduct(x => x)(1, 10))
	}
}
*/

// This program has problem
/*
import math.abs
object Excercise {
    val tolerance = 0.0001
    def isCloseEnough(x: Double, y: Double) = 
        abs((x - y) / x) / x < tolerance

    def fixedPoint(f: Double => Double)(guess: Double): Double = {
        def iterate(guess: Double): Double = {
            val result = f(guess)
            if(isCloseEnough(guess, result)) return guess
            else return iterate(result)
        }
        iterate(guess)
    }
    def main(args: Array[String]): Unit = {
    	println(fixedPoint(x => x * x)(-4.0))
	}
}
*/
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