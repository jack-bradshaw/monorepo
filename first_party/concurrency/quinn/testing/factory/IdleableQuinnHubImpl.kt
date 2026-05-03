package com.jackbradshaw.concurrency.quinn.testing.factory

import com.jackbradshaw.closet.resourcemanager.ResourceManager
import com.jackbradshaw.concurrency.quinn.Production
import com.jackbradshaw.concurrency.quinn.Quinn
import com.jackbradshaw.concurrency.quinn.testing.idleable.IdleableQuinn
import com.jackbradshaw.concurrency.quinn.testing.idleable.IdleableQuinnImpl
import javax.inject.Inject
import kotlinx.coroutines.runBlocking
import java.util.UUID


class IdleableQuinnHubImpl @Inject constructor(
  private val resourceManagerFactory: ResourceManager.Factory,
  @Production private val realQuinnFactory: Quinn.Factory,
) : IdleableQuinn.Hub {

  // We use UUID as keys for the resource manager
  private val resourceManager = resourceManagerFactory.createResourceManager<String, IdleableQuinn<*>>()

  override fun <T> createQuinn(): Quinn<T> {
    val idleableQuinn = IdleableQuinnImpl<T>(realQuinnFactory.createQuinn())
    // Add to resource manager. Since it's suspend, we must use runBlocking here, or we can't create it synchronously.
    // Wait, Quinn.Factory.createQuinn() is NOT suspend.
    // So we MUST use runBlocking to mutate the ResourceManager.
    runBlocking {
      resourceManager.put(UUID.randomUUID().toString(), idleableQuinn)
    }
    return idleableQuinn
  }

  override fun isIdle(): Boolean {
    // We are idle if all currently open Quinns are idle.
    // The runBlocking here acts as the transitive lock you designed: if another thread is
    // actively creating a Quinn (holding the RM mutex via put), this will block, which is 
    // correct because it means the system is not yet idle.
    return runBlocking { resourceManager.getAll().all { it.isIdle() } }
  }
}