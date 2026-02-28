package com.jackbradshaw.sasync.inbound.config

import com.jackbradshaw.universal.count.CountKt.bounded
import com.jackbradshaw.universal.frequency.FrequencyKt.unbounded
import com.jackbradshaw.universal.frequency.frequency

val defaultConfig = config {
  refreshRate = frequency { unbounded = unbounded {} }
  bufferSize = bounded {
    // Matches buffered input stream defaults
    value = 8192
  }
}
