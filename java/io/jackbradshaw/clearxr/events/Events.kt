package io.jackbradshaw.clearxr.events

import kotlinx.coroutines.flow.Flow

import io.jackbradshaw.clearxr.model.Input
import io.jackbradshaw.clearxr.model.InteractionProfile
import io.jackbradshaw.clearxr.manifest.encoder.ManifestEncoder

interface Events {
  fun digitalEvent(profile: InteractionProfile, input: Input): Flow<Boolean>
  fun analogEvent(profile: InteractionProfile, input: Input): Flow<Float>
}