package com.jackbradshaw.backstab.oksp.adapters

import com.jackbradshaw.backstab.core.model.BackstabModule
import com.jackbradshaw.obelisk.core.adapters.OutflowAdapter
import com.jackbradshaw.obelisk.core.model.Source
import javax.inject.Inject

class BackstabOutflowAdapter @Inject constructor() : OutflowAdapter<BackstabModule> {
  override fun format(output: BackstabModule) = output.source
}
