package com.jackbradshaw.backstab.annotations.aggregate

import javax.inject.Scope

/**
 * The dagger scope for the flat component space associated with a backstab aggregate component. All
 * components annotated with `@Backstab` that are included in an aggregate component will be
 * included in this scope, but they do not need to be annotated with @AggregateScope. It should only
 * be placed on:
 * 1. Modules defined to inegrate third party components.
 * 2. Aggregate components.
 */
@Scope @Retention(AnnotationRetention.RUNTIME) annotation class AggregateScope
