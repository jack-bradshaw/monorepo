package io.jackbradshaw.otter.physics.experiment.items

import dagger.Binds

@Module
interface ItemsModule {
  @Binds
  fun bindCube(impl: CubeImpl): Cube

  @Binds
  fun bindLab(impl: LabImpl): Lab

  @Binds
  fun bindOrigin(impl: OriginImpl): Origin
}