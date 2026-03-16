class InterceptorDispatcherImpl(delegate: CoroutineDispatcher) : InterceptorDispatcher {

  private val running = ConcurrentHashSet<Runnable>()

  override fun dispatch(context: CoroutineContext, block: Runnable) {
    val wrapper = object : Runnable {
      override fun run() {
        running.add(this)
        block()
        running.remove(this)
      }
    }

    delegate.dispatch(context, wrapper)
  }

  override fun isDispatchNeeded(context: CoroutineContext) = delegate.isDispatchNeeded

  override fun dispatchYield(context: CoroutineContext, block: Runnable) {
    val wrapper = object : Runnable {
      override fun run() {
        running.add(this)
        block()
        running.remove(this)
      }
    }

    delegate.dispatchYield(context, wrapper)
  }

  override fun isIdle() = running.isEmpty()
}