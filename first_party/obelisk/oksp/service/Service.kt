package com.jackbradshaw.obelisk.oksp.service

import com.jackbradshaw.obelisk.core.services.ObeliskControlService
import com.jackbradshaw.obelisk.core.services.ObeliskDataService
import com.jackbradshaw.obelisk.core.services.ObeliskErrorService
import com.jackbradshaw.obelisk.core.services.ObeliskLoggingService

import com.google.devtools.ksp.symbol.KSNode
import com.jackbradshaw.obelisk.core.adapters.InflowAdapter
import com.jackbradshaw.obelisk.core.adapters.OutflowAdapter

interface Service<A, R> : ObeliskControlService, ObeliskDataService<A, R>, ObeliskErrorService<A>, ObeliskLoggingService<A> {
  interface Factory {
    fun <A, R> create(inflow: InflowAdapter<KSNode, A>, outflow: OutflowAdapter<R>): Service<A, R>
  }
}