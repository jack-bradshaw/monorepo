package com.jackbradshaw.coroutines

import com.jackbradshaw.coroutines.io.Io
import kotlinx.coroutines.CoroutineScope

interface CoroutinesComponent {
  @Io fun ioCoroutineScope(): CoroutineScope
}
