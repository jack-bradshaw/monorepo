package com.jackbradshaw.sealant.outlet

import com.jackbradshaw.sealant.connectable.Connectable
import kotlinx.coroutines.flow.Flow

/** 
 * The final terminal pipe. It exposes the data flow, but cannot be passed into another [Hub], 
 * structurally enforcing that it is the end of the line.
 */
interface Outlet<T> : Connectable {
  val flow: Flow<T>
}
