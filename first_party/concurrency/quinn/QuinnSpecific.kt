package com.jackbradshaw.concurrency.quinn

import javax.inject.Qualifier

/**
 * Applied to generic framework types to distinguish them from instances provided by other packages
 * (thus avoiding duplicate binding exceptions in complex Dagger graphs).
 */
@Qualifier @Retention(AnnotationRetention.RUNTIME) annotation class QuinnSpecific
