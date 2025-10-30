package com.jackbradshaw.sasync.transport.outbound

import kotlinx.coroutines.channels.SendChannel

interface Outbound {

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
}
