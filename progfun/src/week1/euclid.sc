object euclid {

  // tail recursion, execute in constant stack space
  def gcd(a: Int, b: Int): Int =
    if (b == 0) a else gcd(b, a % b)

  gcd(14, 21)
  // gcd(21, 14)
  // gcd(14, 7)
  // gcd(7, 0)


  // not tail recursion, need to keep intermediate steps
  def factorial(n: Int): Int =
    if (n == 0) 1 else n * factorial(n-1)

  factorial(5) // (5 * (4 * (3 * (2 * (1 * 1)))))
  factorial(4) // (4 * (3 * (2 * (1 * 1))))
  factorial(3) // (3 * (2 * (1 * 1)))
  factorial(2) // (2 * (1 * 1))
  factorial(1) // (1 * 1)
  factorial(0) // 1



  // tail recursion, execute in constant stack space
  def factorialTwo(n: Int): Int = {
    def loop(acc: Int, n: Int): Int =
      if (n == 0) acc
      else loop(acc * n, n - 1)
    loop(1, n)
  }

  factorialTwo(5)
  // loop(1*5, 4)
  // loop(5*4, 3)
  // loop(20*3, 2)
  // loop(60*2, 1)
  // loop(120*1, 0)
  // 120

  factorialTwo(1)
  // loop(1*1, 0)
  // 1
}