interface SealedSource<T> : SealedHub<T> {
  suspend fun emit(value: T)
}