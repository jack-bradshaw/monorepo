package com.foo

class MultiTypeArguments<K, out V, in T>

class MultiKey

class MultiValue

class MultiTrigger

class MultiClass {
  lateinit var multiField: MultiTypeArguments<MultiKey, MultiValue, MultiTrigger>
}

val testPropertyMultipleTypeArguments: MultiTypeArguments<MultiKey, MultiValue, MultiTrigger> =
    TODO()
