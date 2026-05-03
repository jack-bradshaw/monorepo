package com.jackbradshaw.concurrency.quinn

import javax.inject.Qualifier

/** Specifies the production-grade implementation. */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Production

/** Specifies the quinn-specific implementation. */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class QuinnQualifier
