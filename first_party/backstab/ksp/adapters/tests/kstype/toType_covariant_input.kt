package com.foo

class CovarCovariant<out T>

class CovarBound

class CovarClass {
  lateinit var covariantField: CovarCovariant<CovarBound>
}

val testPropertyCovariant: CovarCovariant<CovarBound> = TODO()
