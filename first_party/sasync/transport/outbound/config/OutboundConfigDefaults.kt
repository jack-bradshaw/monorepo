package com.jackbradshaw.sasync.transport.outbound.config

import com.jackbradshaw.model.count.CountKt.unbounded
import com.jackbradshaw.model.count.count

val defaultConfig = config { queueSize = count { unbounded = unbounded {} } }
