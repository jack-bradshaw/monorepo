package io.jackbradshaw.otter.openxr.input

import dagger.Module
import dagger.Binds

@Module
interface OpenXrUserInputsModule {
  @Binds
  fun bindOpenXrUserInputs(impl: OpenXrUserInputsImpl): OpenXrUserInputs
}