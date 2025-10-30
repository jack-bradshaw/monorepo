package com.jackbradshaw.sasync.transport.outbound

import com.jackbradshaw.coroutines.io.Io
import com.jackbradshaw.model.count.Count
import com.jackbradshaw.sasync.transport.outbound.config.Config
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.io.OutputStream
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.launch

class OutboundImpl
@AssistedInject
constructor(
    @Io private val ioScope: CoroutineScope,
    config: Config,
    @Assisted private val destination: OutputStream,
) : Outbound {

  private val channel: Channel<ByteArray> by lazy {
    when (config.queueSize.typeCase) {
      Count.TypeCase.UNBOUNDED -> Channel<ByteArray>(Channel.UNLIMITED)
      Count.TypeCase.BOUNDED ->
          Channel<ByteArray>(capacity = config.queueSize.bounded.value.toInt())
      else -> throw IllegalStateException("Queue size not set in config: ${config}")
    }
  }

  init {
    ioScope.launch {
      for (array in channel) {
        destination.write(array)
      }
    }
  }

  override suspend fun asChannel(): SendChannel<ByteArray> = channel

  override suspend fun publishInt(int: Int) {
    channel.send(ByteArray(1) { int.toByte() })
  }

  override suspend fun publishIntLine(int: Int) {
    publishInt(int)
    publishLineEnding()
  }

  override suspend fun publishString(string: String) {
    channel.send(string.toByteArray())
  }

  override suspend fun publishStringLine(string: String) {
    publishString(string)
    publishLineEnding()
  }

  override suspend fun publishBytes(bytes: ByteArray) {
    channel.send(bytes)
  }

  override suspend fun publishBytesLine(bytes: ByteArray) {
    publishBytes(bytes)
    publishLineEnding()
  }

  override suspend fun publishBytes(bytes: Array<Byte>) {
    channel.send(bytes.toByteArray())
  }

  override suspend fun publishBytesLine(bytes: Array<Byte>) {
    publishBytes(bytes)
    publishLineEnding()
  }

  override suspend fun publishLineEnding() {
    channel.send(System.lineSeparator().toByteArray())
  }
}

@AssistedFactory
public interface OutboundImplFactory {
  fun create(destination: OutputStream): OutboundImpl
}
