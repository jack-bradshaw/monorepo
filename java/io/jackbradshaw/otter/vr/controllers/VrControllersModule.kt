package io.jackbradshaw.otter.vr.controllers

import dagger.Binds
import dagger.Module

@Module
interface VrControllersModule {
  @Binds
  fun bindVrControllers(impl: VrControllers): VrControllers
}