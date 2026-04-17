package com.jackbradshaw.quinn

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ClosedSendChannelException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

/**
 * Default implementation of [Quinn].
 *
 * The implementation turns execution into a pub/sub mechanism. Blocks can be queued asynchronously
 * from any coroutine context and executed sequentially by the consumer's event loop via [drain].
 * This approach is necessary for thread-sensitive resources that must stay locked on a single
 * sequential loop but demand asynchronous aggregation.
 */
class QuinnImpl<T> @Inject constructor() : Quinn<T> {

  /** 
   * Guards closure.
   * 
   * This lock is used in two places:
   * 
   * 1. Guarding setting [_hasTerminalState] to `true` when entering [close].
   * 2. Guarding dequeueing and evaluating a `block` in [tryDrain].
   * 
   * This guards against a race condition:
   * 
   * 1. On thread 1: The [tryDrain] function checks the state, receives `open`, and dequeues the
   * next block to run.
   * 2. On thread 2: The [close] function is called and sets the closed state to `closed`.
   * 3. On thread 1: The dequeued block is evaluated.
   * 
   * This is erroneous because it means processing is continuing after closure has returned and
   * reported all processing terminated. By guarding both in a mutex, the race condition is
   * eliminated. Normally evaluating a `block` in the context of a mutex would be erroneous because
   * it can suspend, thus holding the lock indefinitely; however, in this case that is exactly the desired
   * behaviour. When [close] is called, it should block the calling thread until the presently
   * evaluated `block` is finished, even if processing has only dequeued it so far.
   */
  private val closureLock = Mutex()

  /** Guards [drain] and [tryDrain].
   * 
   * Only the first call should execute drainage. This ensures concurrent calls suspend until
   * existing calls exit.
   */
  private val drainLock = Mutex()

  /** Whether this has entered a terminal state. */
  private val _hasTerminalState = MutableStateFlow(false)

  /** Whether this has finished all ongoing processing. */
  private val _hasTerminatedProcesses = MutableStateFlow(false)

  /** The queue of unprocessed blocks. */
  private val blockQueue = Channel<ConsumableBlock<T>>(BLOCK_QUEUE_BUFFER_SIZE)

  override val hasTerminalState = _hasTerminalState.asStateFlow()

  override val hasTerminatedProcesses = _hasTerminatedProcesses.asStateFlow()

  override suspend fun submit(block: (T) -> Unit) {
    if (!trySubmit(block)) {
      error("This Quinn instance is closed, submit cannot be used.")
    }
  }

  override suspend fun trySubmit(block: (T) -> Unit): Boolean {
    if (hasTerminalState.value) return false

    val consumableBlock = ConsumableBlock(block)

    /* 
     * Uses a try/catch to guard against the race condition where the resource is closed between the
     * closure check at the beginning of the function and the subsequent `send`.
     *
     * While it's better to avoid catches where possible, the alternative of putting the `send`
     * inside the closure check is unacceptable, because it requires holding the lock until the
     * blockQueue can accept the element, which involves placing a potential suspending point within
     * a lock check, which is an antipattern.
     * 
     * The closure lock is not used here (unlike [close] and [tryDrain]) because that would cause
     * suspension until the presently executing block has completed. This function should be an
     * immediate queue-and-return operation so suspending indefinitely while an arbitrary operation
     * completes would break the contract.
     */
    try {
      blockQueue.send(consumableBlock)
    } catch (e: ClosedSendChannelException) {
      return false
    }

    consumableBlock.isConsumed.filter { it }.first()
    return true
  }

  override suspend fun drain(resource: T) {
    if (hasTerminalState.value) return

    drainLock.withLock {
      if (hasTerminalState.value) return

      for (consumableBlock in blockQueue) {
        var shouldBreak = false

        closureLock.withLock {
          if (!hasTerminalState.value) {
            consumableBlock.block.invoke(resource)
            consumableBlock.isConsumed.value = true
          } else {
            consumableBlock.isConsumed.value = true
            shouldBreak = true
          }
        }
        
        if (shouldBreak) break
      }
    }
  }

  override fun close() {
    runBlocking {
      closureLock.withLock {
        _hasTerminalState.value = true
      }
      blockQueue.close()
      for (pendingBlock in blockQueue) {
        pendingBlock.isConsumed.value = true
      }
      _hasTerminatedProcesses.value = true
    }
  }

  private companion object {
    /**
     * The size of the block buffer.
     *
     * Chosen as a vast overestimate to ensure queue insertion does not suspend in most cases.
     */
    private const val BLOCK_QUEUE_BUFFER_SIZE = 10000
  }

  /** Factory that provides [QuinnImpl] instances. */
  class FactoryImpl @Inject internal constructor() : Quinn.Factory {
    override fun <T> createQuinn(): Quinn<T> = QuinnImpl()
  }
}

/**
 * A block and an associated observable flag to track whether the block has been consumed.
 *
 * The flag must be externally set because [block] will not automatically update it when it
 * completes. The flag does not necessarily mean the block ran, but rather it has been processed,
 * with the processor acting as the ultimate arbiter of whether to run the block or skip over it.
 */
private data class ConsumableBlock<T>(
    val block: (T) -> Unit,
) {
  val isConsumed = MutableStateFlow(false)
}