package org.test.stacktrace

object Foo:
  def foo: Int = bar
  def bar: Int = baz
  def baz: Int = throw new RuntimeException("baz")

