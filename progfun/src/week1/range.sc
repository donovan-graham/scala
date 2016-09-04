

def nth[T](n: Int, xs: List[T]): T = {
  if (xs.isEmpty) throw new IndexOutOfBoundsException("nth.nothing")
  else if (n == 0) xs.head
  else nth(n - 1, xs.tail)
}

nth(0, List(1,2,3))
nth(1, List(1,2,3))
nth(2, List(1,2,3))
nth(-1, List(1,2,3))
nth(3, List(1,2,3))