package com.jackbradshaw.backstab.core.model.kotlinpoet

import com.jackbradshaw.backstab.core.model.Type
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName

/** Converts this [Type] to an equivalent Kotlin Poet [TypeName]. */
fun Type.toTypeName(): TypeName {
  val className = ClassName(packageName, nameChain)
  val baseType =
      if (typeArguments.isEmpty()) className
      else className.parameterizedBy(typeArguments.map { it.toTypeName() })

  return baseType.copy(nullable = isNullable)
}
