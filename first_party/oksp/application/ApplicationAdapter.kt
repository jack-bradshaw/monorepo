package com.jackbradshaw.oksp.application

/**
 * [Application] with empty instances for all functions.
 *
 * Provided so implementations that do not require some functions are not forced to implement them.
 *
 * For example:
 * ```
 * class NoTeardownApplication : ApplicationAdapter() {
 *   override suspend fun onCreate(component: Application.KspComponent) {
 *     // foo
 *   }
 *
 *   // no need to override onDestroy
 * }
 * ```
 */
abstract class ApplicationAdapter : Application {
  override suspend fun onCreate(component: Application.KspComponent) {}

  override suspend fun onDestroy() {}
}
