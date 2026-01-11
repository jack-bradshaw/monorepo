package com.jackbradshaw.backstab.annotations

import javax.inject.Scope

/**
 * Scope for the Meta-Component graph.
 *
 * Objects annotated with this scope (including Backstab-generated components)
 * are singletons within the Meta-Component's lifecycle.
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class MetaScope
