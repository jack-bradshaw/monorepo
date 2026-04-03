package com.jackbradshaw.coroutines

import javax.inject.Scope

/** Dagger component scope for instances bound to the lifecycle of the [CoroutinesComponent].
 * 
 * Not to be confused with a [CoroutineScope]. This is a Dagger scope for coroutine-related
 * bindings.
*/
@Scope @Retention(AnnotationRetention.RUNTIME) annotation class CoroutinesDaggerScope
