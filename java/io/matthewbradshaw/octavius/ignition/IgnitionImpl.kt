package io.matthewbradshaw.octavius.ignition

import io.matthewbradshaw.octavius.OctaviusScope
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.filter

@OctaviusScope
class IgnitionImpl @Inject constructor() : Ignition {

  private val _started = MutableStateFlow(false)

  override fun started() = _started.filter { it == true }.map { Unit }
  override suspend fun go() {
    _started.value = true
  }
}