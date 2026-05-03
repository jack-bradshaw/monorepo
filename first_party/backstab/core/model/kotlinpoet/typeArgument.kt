package com.jackbradshaw.backstab.core.model.kotlinpoet

import com.jackbradshaw.backstab.core.model.Type
import com.squareup.kotlinpoet.STAR
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.WildcardTypeName

/** Convert this [Type.TypeArgument] to an equivalent Kotlin Poet [TypeName]. */
fun Type.TypeArgument.toTypeName(): TypeName {
  return when (this) {
    is Type.TypeArgument.Star -> STAR
    is Type.TypeArgument.Specific -> {
      val baseType = type.toTypeName()
      when (variance) {
        Type.TypeArgument.Variance.INVARIANT -> baseType
        Type.TypeArgument.Variance.COVARIANT -> WildcardTypeName.producerOf(baseType)
        Type.TypeArgument.Variance.CONTRAVARIANT -> WildcardTypeName.consumerOf(baseType)
      }
    }
  }
}
