package com.jackbradshaw.sealant.source

import com.jackbradshaw.closet.observable.helpers.checkOpen
import com.jackbradshaw.sealant.SealantScope
import com.jackbradshaw.sealant.hub.SealedHub
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow

/** Default implementation of [SealedSource]. */
class SealedSourceImpl<T>(
    private val delegate: SealedHub<T>,
    private val sharedFlow: MutableSharedFlow<T>
) : SealedSource<T>, SealedHub<T> by delegate {

  override suspend fun emit(value: T) {
    checkOpen()
    sharedFlow.emit(value)
  }

  @SealantScope
  class Factory @Inject internal constructor(private val hubFactory: SealedHub.Factory) :
      SealedSource.Factory {

    override suspend fun <T> create(): SealedSource<T> {
      val sharedFlow = MutableSharedFlow<T>()
      val delegate = hubFactory.create(sharedFlow)
      return SealedSourceImpl(delegate, sharedFlow)
    }
  }
}
