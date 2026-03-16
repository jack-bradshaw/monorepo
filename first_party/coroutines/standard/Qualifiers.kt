package com.jackbradshaw.coroutines.io

import javax.inject.Qualifier

/** Qualifier for types related to IO-bound work. */
@Qualifier @Retention(AnnotationRetention.RUNTIME) annotation class Io

/** Qualifier for types related to CPU-bound work. */
@Qualifier @Retention(AnnotationRetention.RUNTIME) annotation class Cpu

/** Qualifier for types related to IO-bound work used within Dagger graph but not exposed. */
@Qualifier @Retention(AnnotationRetention.RUNTIME) annotation class IoIntermediate

/** Qualifier for types related to CPU-bound work used within Dagger graph but not exposed. */
@Qualifier @Retention(AnnotationRetention.RUNTIME) annotation class CpuIntermediate