package io.jackbradshaw.otter.userio

import dagger.Module
import dagger.Binds

@Module
interface VrControllerModule {
  @Binds
  fun bindVrController(impl: VrControllerImpl): VrController
}