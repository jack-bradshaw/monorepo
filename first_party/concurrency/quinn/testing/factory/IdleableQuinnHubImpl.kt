package com.jackbradshaw.concurrency.quinn.testing.factory

import com.jackbradshaw.closet.resourcemanager.set.ResourceSet
import com.jackbradshaw.concurrency.quinn.Production
import com.jackbradshaw.concurrency.quinn.Quinn
import com.jackbradshaw.concurrency.quinn.testing.idleable.IdleableQuinn
import com.jackbradshaw.concurrency.quinn.testing.idleable.IdleableQuinnImpl



import javax.inject.Inject
import kotlinx.coroutines.runBlocking

class IdleableQuinnHubImpl
@Inject
constructor(
    private val resourceSetFactory: ResourceSet.Factory,
    @Production private val realQuinnFactory: Quinn.Factory,
) : IdleableQuinn.Hub {

  private val resourceSet =
      runBlocking { resourceSetFactory.createResourceSet<IdleableQuinn<*>>() }

  override suspend fun <T> createQuinn(): Quinn<T> {
    val idleableQuinn = IdleableQuinnImpl<T>(realQuinnFactory.createQuinn())
    resourceSet.add(idleableQuinn)
    return idleableQuinn
  }

  override fun isIdle(): Boolean {
    return runBlocking { resourceSet.getAll().all { it.isIdle() } }
  }
}
