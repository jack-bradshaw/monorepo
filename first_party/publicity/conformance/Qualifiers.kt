package com.jackbradshaw.publicity.conformance

import javax.inject.Qualifier

/** Qualifier for the directory representing the Bazel workspace root. */
@Qualifier @Retention(AnnotationRetention.RUNTIME) annotation class WorkspaceRoot

/** Qualifier for the directory name that counts as "first party". */
@Qualifier @Retention(AnnotationRetention.RUNTIME) annotation class FirstPartyRoot
