package com.jackbradshaw.concurrency.quinn.testing.hub

import com.jackbradshaw.concurrency.quinn.Quinn
import com.jackbradshaw.concurrency.quinn.QuinnScope
import com.jackbradshaw.concurrency.quinn.testing.idleable.IdleableQuinn
import com.jackbradshaw.concurrency.quinn.testing.idleable.IdleableQuinnImpl
import com.jackbradshaw.concurrency.quinn.testing.prod.Prod
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import kotlinx.coroutines.runBlocking

/** Default implementation of [IdleableQuinnHub]. */
@QuinnScope
class IdleableQuinnHubImpl @Inject constructor(@Prod private val delegate: Quinn.Factory) :
    IdleableQuinnHub {

  /** All provisioned [Quinn] instances. */
  private val provisioned = ConcurrentHashMap.newKeySet<IdleableQuinn<*>>()

  override suspend fun <T> createQuinn() =
      IdleableQuinnImpl<T>(delegate.createQuinn()).also { provisioned.add(it) }

  override fun isIdle() = runBlocking<Boolean> { provisioned.all { it.isIdle() } }
}
