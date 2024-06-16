package io.jackbradshaw.queen.sustainment.operations

import io.jackbradshaw.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.queen.sustainment.primitives.Sustainable
import kotlin.reflect.typeOf
import kotlinx.coroutines.Job
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.launch

/** A [Sustainable.Operation] that uses coroutines for the work. */
abstract class KtCoroutineOperation : Operation<Job> {

  override val workType = WORK_TYPE

  companion object {
    // Caching the value in static memory avoids repeatedly making reflective calls.
    val WORK_TYPE = typeOf<Job>()
  }
}

/** Convenience function for building a new coroutine-based Operation. */
fun ktCoroutineOperation(workBuilder: () -> Job) =
    object : KtCoroutineOperation() {
      override fun work() = workBuilder()
    }
/** Convenience function for building a new coroutine-based Operation. */
fun ktCoroutineOperation(scope: CoroutineScope, workBuilder: suspend () -> Unit) =
  object : KtCoroutineOperation() {
    override fun work(): Job = scope.launch { workBuilder() }
  }

/** Convenience function for building a coroutine-based Sustainable. */
fun ktCoroutineSustainable(workBuilder: () -> Job) = object : Sustainable<Operation<Job>> {
  override val operation = ktCoroutineOperation(workBuilder)
}

/** Convenience function for building a coroutine-based Sustainable. */
fun ktCoroutineSustainable(scope: CoroutineScope, workBuilder: suspend () -> Unit) = object : Sustainable<Operation<Job>> {
  override val operation = ktCoroutineOperation(scope, workBuilder)
}

  