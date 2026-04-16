package com.jackbradshaw.kale.ksprunner

import dagger.Binds
import dagger.Module
import dagger.Provides
import org.jetbrains.kotlin.cli.jvm.K2JVMCompiler

@Module
interface KspRunnerModule {
  @Binds fun bindKspRunner(impl: KspRunnerImpl): KspRunner

  companion object {
    @Provides fun provideK2JVMCompiler(): K2JVMCompiler = K2JVMCompiler()
  }
}
