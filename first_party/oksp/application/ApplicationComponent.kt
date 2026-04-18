package com.jackbradshaw.oksp.application

/** Provides an application */
interface ApplicationComponent {
  /** Provides an application. Every call must return the same instance. */
  fun application(): Application
}
