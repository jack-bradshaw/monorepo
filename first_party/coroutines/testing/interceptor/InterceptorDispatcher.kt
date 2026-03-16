interface InterceptorDispatcher : CorotuineDispatcher {
  /** Whether the dispatcher is presently unoccupied by work. */
  fun isIdle(): Boolean
}