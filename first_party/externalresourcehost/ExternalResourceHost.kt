/**
 * Controls a resource outside memory that must be cleaned up before process exit.
 *
 * Implementations can not safely assume that once [cleanUp] is called, the process it about to end,
 * and no further calls to its implementation will occur. This assumption is true in a well formed
 * program but is not guaranteed.
 *
 * Implementations can not safely assume the ordering of [cleanUp] calls across the process. Calls
 * may be concurrent or sequential, and no ordering is guaranteed.
 *
 * Implementations can not safely assume that [cleanUp] will be called before process termination. A
 * process may terminate without calling [cleanUp] due to crashes, forced terminations, or other
 * unexpected events. Furthermore a process may not call [cleanUp] by design.
 *
 * Implementation which can not guarantee resource cleanup in all circumstances should document
 * their behavior and any mitigations they employ to reduce resource leakage.
 *
 * Implementation which fail to [cleanUp] should throw exceptions to indicate failure.
 */
interface ExternalResourceHost {
  /** Deletes all resources this process controls outside of memory. */
  suspend fun cleanUp()
}
