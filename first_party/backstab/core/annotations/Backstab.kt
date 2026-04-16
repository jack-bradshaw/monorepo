package com.jackbradshaw.backstab.core.annotations

import dagger.Component

/**
 * Marks a Dagger [Component] for processing so it can be included in an aggregate component.
 *
 * Annotating a Dagger component with [Backstab] triggers the generation of a Dagger [Module] which
 * provides an instance of that component, effectively allowing the component to be used as a
 * dependency in the aggregate graph.
 */
@Target(AnnotationTarget.CLASS) @Retention(AnnotationRetention.RUNTIME) annotation class Backstab
