package com.jackbradshaw.sealant.source

import com.jackbradshaw.sealant.hub.SealedHub
import kotlinx.coroutines.flow.Flow

interface SealedSource<T> : SealedHub<T> {
  suspend fun emit(value: T)
}