package io.jackbradshaw.jockstrap.clock

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Rendering

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Physics

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Real