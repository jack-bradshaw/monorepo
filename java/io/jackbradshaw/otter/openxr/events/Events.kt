package io.jackbradshaw.otter.openxr.events

import io.jackbradshaw.otter.openxr.model.Input
import io.jackbradshaw.otter.openxr.model.InteractionProfile
import kotlinx.coroutines.flow.Flow

interface Events {
  fun digitalEvent(profile: InteractionProfile, input: Input): Flow<Boolean>
  fun analogEvent(profile: InteractionProfile, input: Input): Flow<Float>
}
