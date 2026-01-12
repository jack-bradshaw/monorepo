package com.jackbradshaw.codestone.lifecycle.orchestrator.factory

import com.jackbradshaw.codestone.lifecycle.orchestrator.factory.Platform

/**
 * Provides dynamic access to [PlatformComponent]s based on what is available in the classpath at
 * runtime.
 *
 * This class uses reflection to find and instantiate platform component classes at runtime, making
 * it relatively expensive to construct repeatedly. It is therefore a singleton object.
 */
interface Platforms {
  fun getAll(): Set<Platform<*>>
}
