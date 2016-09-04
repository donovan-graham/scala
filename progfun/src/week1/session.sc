object session {
  1 + 2

  val x = 10
  val y = x + 1; y * y

  def abs(x: Double) = if (x < 0) -x else x

  def squareRoot(x: Double) = {

    def squareRootIteration(guess: Double): Double =
      if (isGoodEnough(guess)) guess
      else squareRootIteration(improveGuess(guess))

    def isGoodEnough(guess: Double) =
      abs(guess * guess - x) / x < 0.001

    def improveGuess(guess: Double) =
      (guess + x / guess) / 2

    squareRootIteration(1.0)
  }

  squareRoot(2)

  squareRoot(4)

  squareRoot(9)

  squareRoot(16)

  squareRoot(0.001)

  squareRoot(0.1e-20)

  squareRoot(1.0e20)

  squareRoot(1.0e50)
}