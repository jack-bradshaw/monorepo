package com.jackbradshaw.sluice

import kotlinx.coroutines.flow.Flow

interface SluiceFactory {
  fun <T> createSluice(underlyingFlow: Flow<T>): Sluice<T>
}
