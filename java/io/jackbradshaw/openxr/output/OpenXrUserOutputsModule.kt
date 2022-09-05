package io.jackbradshaw.otter.openxr.output

import dagger.Binds
import dagger.Module

@Module
interface OpenXrUserOutputsModule {
  @Binds
  fun bindOpenXrUserOutputs(impl: OpenXrUserOutputsImpl): OpenXrUserOutputs
}