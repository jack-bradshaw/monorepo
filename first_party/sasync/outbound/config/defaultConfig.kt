package com.jackbradshaw.sasync.outbound.config

import com.jackbradshaw.universal.count.CountKt.unbounded
import com.jackbradshaw.universal.count.count

val defaultConfig = config { queueSize = count { unbounded = unbounded {} } }
