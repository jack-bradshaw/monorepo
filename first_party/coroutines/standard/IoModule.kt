/** Provides types related to IO-bound work. */
object IoModule {
  @Provides
  @Io
  fun provideDispatcher() : CoroutineDispatcher {
    return Dispatchers.IO
  }

  @Provides
  @Io
  fun provideScope(@Io dispatcher: CoroutineDispatcher) : CoroutineScope {
    return CoroutineScope(dispatcher)
  }
}