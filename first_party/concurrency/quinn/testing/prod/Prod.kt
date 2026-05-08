package com.jackbradshaw.concurrency.quinn.testing.prod

import javax.inject.Qualifier

/** Qualifier to denote the production binding of a class. */
@Qualifier @Retention(AnnotationRetention.RUNTIME) annotation class Prod
