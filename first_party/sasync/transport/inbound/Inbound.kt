package com.jackbradshaw.sasync.transport.inbound

import kotlinx.coroutines.flow.SharedFlow

interface Inbound {
  fun observeFlattened(): SharedFlow<Byte>

  fun observeBuffered(): SharedFlow<ByteArray>
}
