package io.jackbradshaw.otter.openxr.events

import kotlinx.coroutines.flow.Flow

import io.jackbradshaw.otter.openxr.model.Input
import io.jackbradshaw.otter.openxr.model.InteractionProfile
import io.jackbradshaw.otter.openxr.manifest.encoder.ManifestEncoder

interface Events {
  fun digitalEvent(profile: InteractionProfile, input: Input): Flow<Boolean>
  fun analogEvent(profile: InteractionProfile, input: Input): Flow<Float>
}