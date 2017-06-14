
  object sample extends App{

    def fib(n: Int, first: Int=0 ,second: Int=1): Int= {
      if (n == 1)
        first
      else
        first + fib(n - 1, second, first + second)

    }
    println(fib(10))
  }
