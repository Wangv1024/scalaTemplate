import annotation.tailrec
object example {
	def sum(f: Int => Int)(a: Int, b: Int): Int = {
		
		@tailrec def loop(a: Int,b: Int, acc: Int): Int = {
			if(a > b)
				return acc
			else 
				return loop(a + 1, b, acc + f(a))
		}
		loop(a, b, 0)
	}	

	def main(args: Array[String]): Unit ={
		println(sum(x => x)(1, 100))
	}
}
