package org.test.stacktrace

import zio._
import zio.test.Assertion.*
import zio.test.{TestAspect, *}

object FooSpec extends ZIOSpecDefault:
  val spec: Spec[Environment, Any] =
    suite("Foo")(
      test("call foo() inside assert()") {
        /* This test fails with the correct stack trace:
          ✗ ERROR: java.lang.RuntimeException: baz
            org.test.stacktrace.Foo$.baz(Foo.scala:6)
            org.test.stacktrace.Foo$.bar(Foo.scala:5)
            org.test.stacktrace.Foo$.foo(Foo.scala:4)
            org.test.stacktrace.FooSpec$.$init$$$anonfun$1$$anonfun$1(FooSpec.scala:23)
            zio.test.Assertion$.value0$lzyINIT1$1(Assertion.scala:54)
            zio.test.Assertion$.value0$1(Assertion.scala:54)
            zio.test.Assertion$.smartAssert$$anonfun$1(Assertion.scala:62)
          Foo.foo did not satisfy equalTo(42)
          at /Users/boisvert/scala/test-stacktrace/src/test/scala/org/test/stacktrace/FooSpec.scala:23          
        */
        assert(Foo.foo)(equalTo(42)) // throws RuntimeException("baz")
      },

      test("call foo() within test()") {
        /* This test fails with an incomplete stack trace:
          Exception in thread "zio-fiber-51" java.lang.RuntimeException: baz
              at org.test.stacktrace.Foo$.baz(Foo.scala:6)
              at org.test.stacktrace.FooSpec.spec(FooSpec.scala:34)       
        */
        val foo = Foo.foo // throws RuntimeException("baz")
        assert(foo)(equalTo(42))
      }
    )
