package com.jackbradshaw.sasync.inbound.transport

import java.io.InputStream
import kotlinx.coroutines.flow.SharedFlow

interface InboundTransport {
  fun observeFlattened(): SharedFlow<Byte>

  fun observeBuffered(): SharedFlow<ByteArray>

  fun interface Factory {
    fun create(source: InputStream): InboundTransport
  }
}
