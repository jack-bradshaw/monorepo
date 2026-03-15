package com.jackbradshaw.closet.resourcemanager

import org.junit.Test
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.launch
import kotlinx.coroutines.coroutineScope
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.closet.resourcemanager.ResourceManager.ManagedResource

abstract class ManagedResourceTest<T : ManagedResource> {

  abstract fun subject(): T

  @Test
  fun close_isIdempotent() = runBlocking {
    val resource = subject()

    resource.close()
    resource.close()
    resource.close()

    // Test passes if no exception is thrown
  }

  @Test
  fun enterTerminalState_isIdempotent() = runBlocking {
    val resource = subject()

    resource.enterTerminalState()
    resource.enterTerminalState()
    resource.enterTerminalState()

    // Test passes if no exception is thrown
  }

  @Test
  fun awaitProcessTermination_isIdempotent() = runBlocking {
    val resource = subject()

    resource.awaitProcessTermination()
    resource.awaitProcessTermination()
    resource.awaitProcessTermination()
    
    // Test passes if no exception is thrown
  }
}