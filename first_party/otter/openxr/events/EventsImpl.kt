package com.jackbradshaw.otter.openxr.events

import com.jackbradshaw.otter.OtterScope
import com.jackbradshaw.otter.openxr.manifest.encoder.ManifestEncoder
import com.jackbradshaw.otter.openxr.model.Input
import com.jackbradshaw.otter.openxr.model.InteractionProfile
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
