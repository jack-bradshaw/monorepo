package com.foo

class ContraContravariant<in T>

class ContraBound

class ContraClass {
  lateinit var contravariantField: ContraContravariant<ContraBound>
}

val testPropertyContravariant: ContraContravariant<ContraBound> = TODO()
