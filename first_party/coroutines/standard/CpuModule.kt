/** Provides types related to CPU-bound work. */
object CpuModule {
  @Provides
  @Cpu
  fun provideDispatcher(@CpuIntermediate actual: CoroutineDispatcher) : CoroutineDispatcher {
    return actual
  }

  @Provides
  @Cpu
  fun provideScope(@CpuIntermediate actual: CoroutineScope) : CoroutineScope {
    return CoroutineScope(dispatcher)
  }
}