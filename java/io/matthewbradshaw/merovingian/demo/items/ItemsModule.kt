package io.matthewbradshaw.merovingian.demo.items

import dagger.Binds
import dagger.Module

@Module
interface ItemsModule {
  @Binds
  fun bindCube(impl: CubeImpl): Cube

  @Binds
  fun bindCubeSwarm(impl: CubeSwarmImpl): CubeSwarm

  @Binds
  fun bindCubeWorld(impl: CubeWorldImpl): CubeWorld
}