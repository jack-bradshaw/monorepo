package io.matthewbradshaw.octavius.ignition

import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.filter

class IgnitionImpl @Inject constructor() : Ignition {

  private val _started = MutableStateFlow(false)

  override fun started() = _started.filter { it == true }.map { Unit }
  override suspend fun ignite() {
    _started.value = true
  }
}