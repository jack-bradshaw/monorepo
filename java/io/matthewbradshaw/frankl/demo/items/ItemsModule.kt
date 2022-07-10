package io.matthewbradshaw.frankl.demo.items

import dagger.Binds

@Module
interface ItemsModule {
  @Binds
  fun bindCube(impl: CubeImpl): Cube

  @Binds
  fun bindCubeSwarm(impl: CubeSwarmImpl): CubeSwarm

  @Binds
  fun bindCubeWorld(impl: CubeLevelImpl): CubeLevel
}