package com.jackbradshaw.backstab.core.generator

import com.jackbradshaw.backstab.core.model.BackstabComponent
import com.squareup.kotlinpoet.FileSpec

interface MetaComponentGenerator {
  suspend fun generate(component: BackstabComponent): FileSpec
}
