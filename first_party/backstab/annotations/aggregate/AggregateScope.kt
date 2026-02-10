package com.jackbradshaw.backstab.annotations.aggregate

import javax.inject.Scope

/**
 * The dagger scope for the flat component space associated with an aggregate component.
 * 
 * This scope should be placed on aggregate components and modules that bind third party components
 * into aggregate components. It should not be placed on `@Backstab`-annotated components.
 */
@Scope @Retention(AnnotationRetention.RUNTIME) annotation class AggregateScope
