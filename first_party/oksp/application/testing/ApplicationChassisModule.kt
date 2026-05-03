package com.jackbradshaw.oksp.testing.application.chassis

import dagger.Binds
import dagger.Module

@Module
interface ApplicationChassisModule {
  @Binds fun bind(impl: ApplicationChassisImpl): ApplicationChassis
}
