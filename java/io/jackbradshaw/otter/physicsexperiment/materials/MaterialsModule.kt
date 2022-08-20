package io.jackbradshaw.otter.physics.experiment.materials

import dagger.Binds

@Module
interface MaterialsModule {
  @Binds
  fun bindMaterials(impl: MaterialsImpl): Materials
}