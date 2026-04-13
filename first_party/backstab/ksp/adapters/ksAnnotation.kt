package com.jackbradshaw.backstab.ksp.adapters

import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.jackbradshaw.backstab.core.model.BackstabTarget
import com.jackbradshaw.backstab.core.typeregistry.JavaxTypeRegistry

/**
 * Returns whether this annotation is a Dagger qualifier (a @Named annotation or an annotation
 * meta-annotated with @Qualifier).
 */
fun KSAnnotation.isQualifier(): Boolean {
  return isDaggerNamedQualifier() || isDaggerCustomQualifier()
}

/**
 * Converts this annotation to a Backstab qualifier.
 *
 * @throws IllegalArgumentException if this annotation is not a Dagger Qualifier (a @Named
 *   annotation or an annotation meta-annotated with @Qualifier).
 */
fun KSAnnotation.toQualifier(): BackstabTarget.Qualifier? {
  return when {
    isDaggerNamedQualifier() -> toNamedQualifier()
    isDaggerCustomQualifier() -> toCustomQualifier()
    else ->
        throw IllegalArgumentException(
            "This annotation is not a Dagger qualifier (@Named or @Qualifier).")
  }
}

/** Returns whether this annotation has a fully qualified name that matches [qualifiedName]. */
fun KSAnnotation.matches(qualifiedName: String): Boolean {
  return annotationType.resolve().declaration.qualifiedName?.asString() == qualifiedName
}

/** Returns whether this annotation is a Dagger [Named] qualifier. */
private fun KSAnnotation.isDaggerNamedQualifier(): Boolean {
  return matches(JavaxTypeRegistry.NAMED.qualifiedName!!)
}

/** Returns true if this annotation is a Dagger [Qualifier] qualifier. */
private fun KSAnnotation.isDaggerCustomQualifier(): Boolean {
  val declaration = annotationType.resolve().declaration as? KSClassDeclaration ?: return false
  return declaration.annotations.any { it.matches(JavaxTypeRegistry.QUALIFIER.qualifiedName!!) }
}

private fun KSAnnotation.toNamedQualifier(): BackstabTarget.Qualifier.Named {
  val nameArg =
      checkNotNull(arguments.firstOrNull { it.name?.asString() == "value" }) {
        "Every @Named annotation must have a name (e.g. @Named(\"foo\"))) but $this does not."
      }
  val name = nameArg.value as String
  return BackstabTarget.Qualifier.Named(name)
}

private fun KSAnnotation.toCustomQualifier(): BackstabTarget.Qualifier.Custom {
  val declaration = annotationType.resolve().declaration
  val packageName = declaration.packageName.asString()
  val nameChain = declaration.nameChain()
  return BackstabTarget.Qualifier.Custom(packageName, nameChain)
}
