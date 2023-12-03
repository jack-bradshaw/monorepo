package io.jackbradshaw.otter.demo.items

import dagger.Binds
import dagger.Module

@Module
interface ItemsModule {
  @Binds fun bindCube(impl: CubeImpl): Cube

  @Binds fun bindCubeSwarm(impl: CubeSwarmImpl): CubeSwarm

  @Binds fun bindCubeWorld(impl: CubeLevelImpl): CubeLevel
}
