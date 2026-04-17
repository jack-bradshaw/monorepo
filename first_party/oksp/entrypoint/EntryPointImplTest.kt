package com.jackbradshaw.oksp.entrypoint

import com.jackbradshaw.coroutines.coroutinesComponent
import com.jackbradshaw.oksp.application.Application
import com.jackbradshaw.oksp.application.passed.DaggerPassedApplicationComponent
class EntryPointImplTest : EntryPointTest() {

  private lateinit var subject: EntryPoint

  override fun setupSubject(application: Application) {
    val applicationComponent =
        DaggerPassedApplicationComponent.builder().binding(application).build()
    subject = EntryPointImpl(coroutinesComponent(), applicationComponent)
  }

  override fun subject(): EntryPoint = subject
}
