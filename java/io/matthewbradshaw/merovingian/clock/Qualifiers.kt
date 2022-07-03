package io.matthewbradshaw.merovingian.clock

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Rendering

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Physics