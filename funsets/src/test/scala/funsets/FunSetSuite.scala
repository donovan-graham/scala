package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)

    val s4 = Set(-1,1,2,3)
    val s5 = Set(1,3,5,6)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) only contains 1") {
    new TestSets {
      assert(contains(s1, 1), "Singleton of 1 contains 1")
      assert(!contains(s1, 2), "Singleton of 1 does not contain 2")
      assert(!contains(s1, 3), "Singleton of 1 does not contain 3")
    }
  }

  test("singletonSet(2) only contains 2") {
    new TestSets {
      assert(!contains(s2, 1), "Singleton of 2 does not contain 1")
      assert(contains(s2, 2), "Singleton of 2 contains 2")
      assert(!contains(s2, 3), "Singleton of 2 does not contain 3")
    }
  }

  test("singletonSet(3) only contains 3") {
    new TestSets {
      assert(!contains(s3, 1), "Singleton of 3 does not contain 1")
      assert(!contains(s3, 2), "Singleton of 3 does not contain 2")
      assert(contains(s3, 3), "Singleton of 3 contains 3")
    }
  }


  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")

      assert(!contains(s, 10), "Intersect 10")
    }
  }


  test("intersect contains all elements of each set") {
    new TestSets {
      val s = intersect(s4, s5)

      assert(contains(s, 1), "Intersect 1")
      assert(contains(s, 3), "Intersect 3")

      assert(!contains(s, -1), "Intersect -1")
      assert(!contains(s, 2), "Intersect 2")
      assert(!contains(s, 5), "Intersect 5")
      assert(!contains(s, 6), "Intersect 6")

      assert(!contains(s, 10), "Intersect 10")
    }
  }

  test("diff contains all elements not in each set") {
    new TestSets {
      val s = diff(s4, s5)

      assert(contains(s, -1), "Diff -1")
      assert(contains(s, 2), "Diff 2")

      assert(!contains(s, 1), "Diff 1")
      assert(!contains(s, 3), "Diff 3")
      assert(!contains(s, 5), "Diff 5")
      assert(!contains(s, 6), "Diff 6")

      assert(!contains(s, 0), "Diff 0")
      assert(!contains(s, 10), "Diff 10")
      assert(!contains(s, 100), "Diff 100")
    }
  }


  test("diff check 1") {
    val s = diff(Set(1,3,4,5,7,1000), Set(1,2,3,4))

    assert(contains(s, 5), "Diff 5")
    assert(contains(s, 7), "Diff 2")
    assert(contains(s, 1000), "Diff 1000")

    assert(!contains(s, 1), "Diff 1")
    assert(!contains(s, 2), "Diff 2")
    assert(!contains(s, 3), "Diff 3")
    assert(!contains(s, 4), "Diff 4")

    assert(!contains(s, 0), "Diff 0")
    assert(!contains(s, 100), "Diff 100")
  }


  test("diff check 2") {
    val s = diff(Set(1,2,3,4), Set(-1000,0))

    assert(contains(s, 1), "Diff 1")
    assert(contains(s, 2), "Diff 2")
    assert(contains(s, 3), "Diff 3")
    assert(contains(s, 4), "Diff 4")

    assert(!contains(s, -1000), "Diff -1000")
    assert(!contains(s, 0), "Diff 0")
    assert(!contains(s, 10), "Diff 10")
  }



  test("filter subset of `s` for which `p` holds.") {
    new TestSets {
      val a = filter(s4, x => x > 0)

      assert(!contains(a, -1), "Filter -1")
      assert(contains(a, 1), "Filter 1")
      assert(contains(a, 2), "Filter 2")
      assert(contains(a, 3), "Filter 3")

      assert(!contains(a, 10), "Filter 10")


      val b = filter(s5, x => x % 2 == 0)

      assert(!contains(b, 1), "Filter 1")
      assert(!contains(b, 3), "Filter 3")
      assert(!contains(b, 5), "Filter 5")
      assert(contains(b, 6), "Filter 6")

      assert(!contains(b, 10), "Filter 10")
    }
  }

  test("forall bounded integers within `s` satisfy `p`") {
    new TestSets {
      val a1 = forall(s4, x => x > 0)
      assert(!a1, "Forall are positive numbers")

      val b1 = forall(s5, x => x > 0)
      assert(b1, "Forall are positive numbers")


      val a2 = forall(s4, x => x > 0)
      assert(!a2, "Forall are greater than 0")

      val b2 = forall(s5, x => x > 0)
      assert(b2, "Forall are greater than 0")
    }
  }


  test("exists whether there is bounded integer within `s` satisfy `p`") {
    new TestSets {
      val a1 = exists(s4, x => x < 0)
      assert(a1, "exists a negative numbers")

      val b1 = exists(s5, x => x < 0)
      assert(!b1, "exists a negative numbers")

      val a2 = exists(s4, x => x > 0)
      assert(a2, "exists a positive numbers")

      val b2 = exists(s5, x => x > 0)
      assert(b2, "exists a positive numbers")


      val a3 = exists(s4, x => x > 10)
      assert(!a3, "exists a number greater than 10")

      val b3 = exists(s5, x => x > 10)
      assert(!b3, "exists a number greater than 10")

    }
  }


  test("map returns a set transformed by applying `f` to each element of `s`") {
    new TestSets {

      val a1 = map(s4, x => x * x)

      assert(contains(a1, 1), "map 1")
      assert(contains(a1, 4), "map 4")
      assert(contains(a1, 9), "map 9")


      assert(!contains(a1, -1), "map -1")
      assert(!contains(a1, 2), "map 2")
      assert(!contains(a1, 3), "map 3")
      assert(!contains(a1, 5), "map 5")
      assert(!contains(a1, 10), "map 10")
      assert(!contains(a1, 100), "map 100")


      val b1 = map(s5, x => x * x * x)

      assert(contains(b1, 1), "map 1")
      assert(contains(b1, 27), "map 27")
      assert(contains(b1, 125), "map 125")
      assert(contains(b1, 216), "map 216")

      assert(!contains(b1, -1), "map -1")
      assert(!contains(b1, 3), "map 3")
      assert(!contains(b1, 5), "map 5")
      assert(!contains(b1, 6), "map 6")
      assert(!contains(b1, 10), "map 10")
      assert(!contains(b1, 100), "map 100")
    }
  }

}
