package io.jackbradshaw.jockstrap.physics.experiment.items

import dagger.Binds
import dagger.Module

@Module
interface ItemsModule {
  @Binds
  fun bindCube(impl: CubeImpl): Cube

  @Binds
  fun bindLab(impl: LabImpl): Lab

  @Binds
  fun bindOrigin(impl: OriginImpl): Origin
}