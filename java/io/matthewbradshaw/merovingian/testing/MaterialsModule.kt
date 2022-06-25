package io.matthewbradshaw.merovingian.testing

import dagger.Module
import dagger.Binds

@Module
interface MaterialsModule {
  @Binds
  fun bindMaterials(impl: MaterialsImpl): Materials
}