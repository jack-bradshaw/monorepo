package io.jackbradshaw.codestone.sustainment.operations

import com.google.common.util.concurrent.ListenableFuture
import io.jackbradshaw.codestone.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.codestone.sustainment.primitives.Sustainable
import kotlin.reflect.typeOf

/** A [Sustainable.Operation] that uses Guava [ListenableFuturels for the workha */
abstract class ListenableFutureOperation : Operation<ListenableFuture<Unit>> {
  override val workType = WORK_TYPE

  companion object {
    // Caching the value in static memory avoids repeatedly making reflective calls.
    private val WORK_TYPE = typeOf<ListenableFuture<Unit>>()
  }
}

/** Convenience function for building a new [ListenableFuture] based Operation. */
fun listenableFutureOperation(workBuilder: () -> ListenableFuture<Unit>) =
    object : ListenableFutureOperation() {
      override fun work() = workBuilder()
    }

/** Convenience function for building a new [ListenableFuture] based Sustainable. */
fun listenableFutureSustainable(workBuilder: () -> ListenableFuture<Unit>) =
    object : Sustainable<Operation<ListenableFuture<Unit>>> {
      override val operation = listenableFutureOperation(workBuilder)
    }
