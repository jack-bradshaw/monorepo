package com.jackbradshaw.kale.processor

import dagger.Binds
import dagger.Module

@Module
interface ProcessorChassisModule {
  @Binds fun bindProcessorChassis(impl: ProcessorChassisImpl): ProcessorChassis
}
