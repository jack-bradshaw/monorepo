package io.jackbradshaw.otter.demo.materials

import dagger.Binds

@Module
interface MaterialsModule {
  @Binds
  fun bindMaterials(impl: MaterialsImpl): Materials
}