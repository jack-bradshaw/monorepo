package com.jackbradshaw.backstab.ksp.adapters

import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSFile
import java.util.LinkedList

/**
 * Parses the hierarchy of names for this [KSDeclaration] by traversing up the hierarchy.
 *
 * For example: `Outer.Inner.Foo` becomes `['Outer', 'Inner', 'Foo']`.
 */
fun KSDeclaration.nameChain(): List<String> {
  val names = LinkedList<String>()

  var current: KSDeclaration? = this
  while (current != null && current !is KSFile) {
    names.add(0, current.simpleName.asString())
    current = current.parentDeclaration
  }

  return names
}
