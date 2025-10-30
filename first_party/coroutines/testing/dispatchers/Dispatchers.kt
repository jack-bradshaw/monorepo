package com.jackbradshaw.coroutines.testing.dispatchers

import javax.inject.Qualifier

/** Qualifier for test coroutine dispatchers that do not execute work until prompted. */
@Qualifier @Retention(AnnotationRetention.RUNTIME) annotation class Deferred

/** Qualifier for test coroutine dispatchers that eagerly execute work when available. */
@Qualifier @Retention(AnnotationRetention.RUNTIME) annotation class Eager
