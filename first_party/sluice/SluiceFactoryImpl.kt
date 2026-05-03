package com.jackbradshaw.sluice

import com.jackbradshaw.coroutines.Io
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import jakarta.inject.Inject

@SluiceScope
class SluiceFactoryImpl @Inject internal constructor(
    @Io private val ioDispatcher: CoroutineDispatcher
) : SluiceFactory {
  override fun <T> createSluice(underlyingFlow: Flow<T>): Sluice<T> = SluiceImpl(underlyingFlow, ioDispatcher)
}
