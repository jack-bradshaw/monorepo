package io.matthewbradshaw.frankl.physicsexperiment.materials

import dagger.Binds
import dagger.Module

@Module
interface MaterialsModule {
  @Binds
  fun bindMaterials(impl: MaterialsImpl): Materials
}