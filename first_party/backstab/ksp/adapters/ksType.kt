package com.jackbradshaw.backstab.ksp.adapters

import com.google.devtools.ksp.symbol.KSType
import com.jackbradshaw.backstab.core.model.Type

/** Converts this [KSType] to a [Type] model. */
fun KSType.toType(): Type {
  val packageName = declaration.packageName.asString()
  val nameChain = declaration.nameChain()
  val typeArguments = arguments.map { it.toTypeArgument() }
  return Type(packageName, nameChain, typeArguments, isMarkedNullable)
}
