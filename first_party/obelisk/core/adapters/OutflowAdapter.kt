package com.jackbradshaw.obelisk.core.adapters

import com.jackbradshaw.obelisk.core.model.Source

interface OutflowAdapter<R> {
  fun format(output: R): Source
}
