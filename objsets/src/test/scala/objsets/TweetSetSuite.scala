package objsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TweetSetSuite extends FunSuite {
  trait TestSets {
    val set1 = new Empty
    val set2 = set1.incl(new Tweet("a", "a body", 20))
    val set3 = set2.incl(new Tweet("b", "b body", 20))
    val c = new Tweet("c", "c body", 7)
    val d = new Tweet("d", "d body", 9)
    val set4c = set3.incl(c)
    val set4d = set3.incl(d)
    val set5 = set4c.incl(d)
  }

  trait TestSets2 {
    val a = new Tweet("a", "a body", 1)
    val b = new Tweet("b", "b body", 15)
    val c = new Tweet("c", "c body", 12)
    val d = new Tweet("d", "d body", 4)
    val e = new Tweet("e", "e body", 9)

    val set1 = new Empty
    val set2 = set1.incl(a)
    val set3 = set2.incl(b)
    val set4 = set3.incl(c)
    val set5 = set4.incl(d)
    val set6 = set5.incl(e)
  }


  def asSet(tweets: TweetSet): Set[Tweet] = {
    var res = Set[Tweet]()
    tweets.foreach(res += _)
    res
  }

  def size(set: TweetSet): Int = asSet(set).size

  test("filter: on empty set") {
    new TestSets {
      assert(size(set1.filter(tw => tw.user == "a")) === 0)
    }
  }

  test("filter: a on set5") {
    new TestSets {
      assert(size(set5.filter(tw => tw.user == "a")) === 1)
    }
  }

  test("filter: 20 on set5") {
    new TestSets {
      assert(size(set5.filter(tw => tw.retweets == 20)) === 2)
    }
  }

  test("union: set4c and set4d") {
    new TestSets {
      assert(size(set4c.union(set4d)) === 4)
    }
  }

  test("union: with empty set (1)") {
    new TestSets {
      assert(size(set5.union(set1)) === 4)
    }
  }

  test("union: with empty set (2)") {
    new TestSets {
      assert(size(set1.union(set5)) === 4)
    }
  }

  test("mostRetweeted") {
    new TestSets2 {
      val tweet = set6.mostRetweeted
      assert(tweet.retweets == 15)
      assert(tweet.user == "b")
    }
  }

  test("descendingByRetweet") {
    new TestSets2 {
      val trends = set6.descendingByRetweet
      assert(!trends.isEmpty)
      assert(trends.head.retweets == 15)
      assert(trends.head.user == "b")

      assert(trends.tail.head.retweets == 12)
      assert(trends.tail.head.user == "c")

      assert(trends.tail.tail.head.retweets == 9)
      assert(trends.tail.tail.head.user == "e")
    }
  }

  test("descending: set5") {
    new TestSets {
      val trends = set5.descendingByRetweet
      assert(!trends.isEmpty)
      assert(trends.head.user == "a" || trends.head.user == "b")
    }
  }
}
