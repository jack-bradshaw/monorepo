package com.foo

class ComplexOuter {
  class ComplexInner {
    class ComplexDeeplyNested<T>
  }
}

class ComplexBound

interface ComplexInterface {
  fun complexMethod(): ComplexOuter.ComplexInner.ComplexDeeplyNested<ComplexBound?>?
}

val testPropertyComplex: ComplexOuter.ComplexInner.ComplexDeeplyNested<ComplexBound?>? = TODO()
