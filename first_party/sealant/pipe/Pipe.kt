package com.jackbradshaw.sealant.pipe

import com.jackbradshaw.sealant.connectable.Connectable
import kotlinx.coroutines.flow.Flow

/** 
 * A pipe that carries data and can be fanned-out into another [Hub]. 
 */
interface Pipe<T> : Connectable {
  val flow: Flow<T>
}
