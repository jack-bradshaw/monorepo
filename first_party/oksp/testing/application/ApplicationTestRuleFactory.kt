package com.jackbradshaw.oksp.testing.application

import com.jackbradshaw.coroutines.coroutinesComponent
import com.jackbradshaw.kale.ksprunner.JvmSource
import com.jackbradshaw.kale.ksprunner.KspRunnerComponent
import com.jackbradshaw.oksp.application.Application
import dagger.Component

@Component(dependencies = [KspRunnerComponent::class], modules = [ApplicationTestModule::class])
internal interface ApplicationTestComponent {
  @Component.Builder
  interface Builder {
    fun consuming(compiler: KspRunnerComponent): Builder

    fun build(): ApplicationTestComponent
  }

  fun applicationTestRule(): ApplicationTestRule
}

fun applicationTestRule(
    application: Application,
    sources: List<JvmSource>,
    compiler: KspRunnerComponent =
        com.jackbradshaw.kale.ksprunner.kspRunnerComponent(
            coroutines = com.jackbradshaw.coroutines.coroutinesComponent())
): ApplicationTestRule {
  val component = DaggerApplicationTestComponent.builder().consuming(compiler).build()
  return component.applicationTestRule().apply { initialize(application, sources) }
}
