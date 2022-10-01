package io.jackbradshaw.otter.openxr.events

import io.jackbradshaw.otter.OtterScope
import io.jackbradshaw.otter.openxr.manifest.encoder.ManifestEncoder
import io.jackbradshaw.otter.openxr.model.Input
import io.jackbradshaw.otter.openxr.model.InteractionProfile
import kotlinx.coroutines.flow.Flow

@OtterScope
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
