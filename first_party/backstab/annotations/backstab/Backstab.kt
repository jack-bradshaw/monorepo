package com.jackbradshaw.backstab.annotations.backstab

/**
 * Marks a Dagger Component for auto-generation of a Meta-Component module.
 *
 * This annotation triggers the generation of a Dagger Module that provides
 * an instance of this component, constructed via its Builder or Factory,
 * effectively treating the component as a dependency in the meta-graph.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class Backstab
