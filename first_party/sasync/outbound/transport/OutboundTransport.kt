package com.jackbradshaw.sasync.outbound.transport

import java.io.OutputStream
import kotlinx.coroutines.channels.SendChannel

interface OutboundTransport {

  suspend fun asChannel(): SendChannel<ByteArray>

  suspend fun publishInt(int: Int)

  suspend fun publishIntLine(int: Int)

  suspend fun publishString(string: String)

  suspend fun publishStringLine(string: String)

  suspend fun publishBytes(bytes: ByteArray)

  suspend fun publishBytesLine(bytes: ByteArray)

  suspend fun publishBytes(bytes: Array<Byte>)

  suspend fun publishBytesLine(bytes: Array<Byte>)

  suspend fun publishLineEnding()

  suspend fun close()

  fun interface Factory {
    fun create(destination: OutputStream): OutboundTransport
  }
}
