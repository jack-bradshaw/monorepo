package java.io.matthewbradshaw.merovingian.core

interface Lifecycle {
  suspend fun start()
  suspend fun stop()
}