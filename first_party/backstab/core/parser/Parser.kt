package com.jackbradshaw.backstab.core.parser

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.jackbradshaw.backstab.core.model.BackstabComponent

interface Parser {
    fun parseModel(component: KSClassDeclaration): BackstabComponent?
}