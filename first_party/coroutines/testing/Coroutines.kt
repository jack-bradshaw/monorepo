package com.jackbradshaw.coroutines.testing

import javax.inject.Qualifier

/** Applied to generic framework types to distinguish them from instances provided by other
 * packages (thus avoiding duplicate binding exceptions in complex Dagger graphs). */
@Qualifier @Retention(AnnotationRetention.RUNTIME) annotation class Coroutines
