package com.jackbradshaw.backstab.core.model

/** A fully-qualified type with support for type arguments and nullability. */
data class Type(
    val packageName: String,
    val nameChain: List<String>,
    val typeArguments: List<TypeArgument> = emptyList(),
    val isNullable: Boolean = false,
) {

  /** A type argument. */
  sealed class TypeArgument {

    /** A specific type argument with a [type] and [variance]. */
    data class Specific(val type: Type, val variance: Variance) : TypeArgument()

    /** A star projection (`*`). */
    data object Star : TypeArgument()

    /** The variance of a type. */
    enum class Variance {
      /** No variance. */
      INVARIANT,
      /** Covariant (`out` in Kotlin). */
      COVARIANT,
      /** Contravariant (`in` in Kotlin). */
      CONTRAVARIANT,
    }
  }
}
