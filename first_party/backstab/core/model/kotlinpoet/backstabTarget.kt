package com.jackbradshaw.backstab.core.model.kotlinpoet

import com.jackbradshaw.backstab.core.model.BackstabTarget
import com.jackbradshaw.backstab.core.typeregistry.JavaxTypeRegistry
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.asClassName

/** Converts this [BackstabTarget.Qualifier] to an equivalent Kotlin Poet [AnnotationSpec]. */
fun BackstabTarget.Qualifier.toAnnotationSpec(): AnnotationSpec {
  return when (this) {
    is BackstabTarget.Qualifier.Named -> {
      AnnotationSpec.builder(JavaxTypeRegistry.NAMED.asClassName()).addMember("%S", value).build()
    }
    is BackstabTarget.Qualifier.Custom -> {
      AnnotationSpec.builder(ClassName(packageName, nameChain)).build()
    }
  }
}
