package com.jackbradshaw.sasync.inbound.config

import com.jackbradshaw.model.count.CountKt.bounded
import com.jackbradshaw.model.frequency.FrequencyKt.unbounded
import com.jackbradshaw.model.frequency.frequency

val defaultConfig = config {
  refreshRate = frequency { unbounded = unbounded {} }
  bufferSize = bounded {
    // Matches buffered input stream defaults
    value = 8192
  }
}
