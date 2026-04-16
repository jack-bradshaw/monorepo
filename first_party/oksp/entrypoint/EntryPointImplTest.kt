package com.jackbradshaw.oksp.entrypoint

import com.jackbradshaw.coroutines.coroutines
import com.jackbradshaw.oksp.application.Application
import com.jackbradshaw.oksp.application.testing.DaggerBoundInstanceApplicationComponent

class EntryPointImplTest : EntryPointTest() {

  private lateinit var subject: EntryPoint

  override fun setupSubject(application: Application) {
    val applicationComponent =
        DaggerBoundInstanceApplicationComponent.builder().binding(application).build()
    subject = EntryPointImpl(coroutines(), applicationComponent)
  }

  override fun subject(): EntryPoint = subject
}
