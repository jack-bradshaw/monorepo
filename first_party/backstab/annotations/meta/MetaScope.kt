package com.jackbradshaw.backstab.annotations.meta

import javax.inject.Scope

/**
 * A marker annotation for methods that provide a Component instance.
 */
@Scope
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS, AnnotationTarget.PROPERTY_GETTER)
@Retention(AnnotationRetention.RUNTIME)
annotation class MetaScope
