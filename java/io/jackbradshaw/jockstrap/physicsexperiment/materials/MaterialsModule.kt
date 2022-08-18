package io.jackbradshaw.jockstrap.physics.experiment.materials

import dagger.Binds

@Module
interface MaterialsModule {
  @Binds
  fun bindMaterials(impl: MaterialsImpl): Materials
}