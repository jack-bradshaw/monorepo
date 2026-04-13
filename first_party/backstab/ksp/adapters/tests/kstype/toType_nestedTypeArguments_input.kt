package com.foo

class NestedTypeArgWrapper<T>

class NestedTypeArgInner<T>

class NestedTypeArgBound

class NestedTypeArgClass {
  lateinit var nestedGenField: NestedTypeArgWrapper<NestedTypeArgInner<NestedTypeArgBound>>
}

val testPropertyNestedTypeArgerics: NestedTypeArgWrapper<NestedTypeArgInner<NestedTypeArgBound>> =
    TODO()
