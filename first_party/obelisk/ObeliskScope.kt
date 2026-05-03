package com.jackbradshaw.obelisk.core.component

import javax.inject.Scope

/**
 * Defines the Dagger scope for components and provisions that live within the Obelisk library 
 * lifecycle. This guarantees that all injected Obelisk components share singular state during execution.
 */
@Scope
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class ObeliskScope
