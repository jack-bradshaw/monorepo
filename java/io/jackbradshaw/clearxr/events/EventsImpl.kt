package io.jackbradshaw.clearxr.events

import io.jackbradshaw.clearxr.model.Input
import io.jackbradshaw.clearxr.model.InteractionProfile
import io.jackbradshaw.clearxr.manifest.encoder.ManifestEncoder
import kotlinx.coroutines.flow.Flow

class EventsImpl(
    private val manifestEncoder: ManifestEncoder,
) : Events {
  override fun digitalEvent(profile: InteractionProfile, input: Input): Flow<Boolean> {
    TODO()
  }

  override fun analogEvent(profile: InteractionProfile, input: Input): Flow<Float> {
    TODO()
  }
}