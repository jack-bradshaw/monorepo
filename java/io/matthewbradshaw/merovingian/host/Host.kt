package io.matthewbradshaw.merovingian.host

import io.matthewbradshaw.merovingian.model.WorldItem

interface Host {
  suspend fun run(item: WorldItem)
}