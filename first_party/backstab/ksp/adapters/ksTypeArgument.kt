package com.jackbradshaw.backstab.ksp.adapters

import com.google.devtools.ksp.symbol.KSTypeArgument
import com.google.devtools.ksp.symbol.Variance
import com.jackbradshaw.backstab.core.model.Type

/** Converts this [KSTypeArgument] to a [Type.TypeArgument] model. */
fun KSTypeArgument.toTypeArgument(): Type.TypeArgument {
  return when (variance) {
    Variance.INVARIANT ->
        Type.TypeArgument.Specific(resolveType(), Type.TypeArgument.Variance.INVARIANT)
    Variance.COVARIANT ->
        Type.TypeArgument.Specific(resolveType(), Type.TypeArgument.Variance.COVARIANT)
    Variance.CONTRAVARIANT ->
        Type.TypeArgument.Specific(resolveType(), Type.TypeArgument.Variance.CONTRAVARIANT)
    Variance.STAR -> Type.TypeArgument.Star
  }
}

private fun KSTypeArgument.resolveType(): Type {
  val resolvedType = checkNotNull(type) { "This type argument does not have a type" }
  return resolvedType.resolve().toType()
}
