package com.jackbradshaw.backstab.core.annotations

import dagger.Component
import dagger.Module
import javax.inject.Scope

/**
 * The dagger scope for the flat component space associated with an aggregate component.
 *
 * This scope should be placed on aggregate [Component]s and [Module]s that bind third party
 * components into aggregate components. It should not be placed on [Backstab]-annotated components.
 */
@Scope @Retention(AnnotationRetention.RUNTIME) annotation class AggregateScope
