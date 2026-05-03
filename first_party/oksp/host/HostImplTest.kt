package com.jackbradshaw.oksp.host

import com.jackbradshaw.oksp.application.Application
import com.jackbradshaw.oksp.application.passed.DaggerPassedApplicationComponent

class HostImplTest : HostTest() {

  private lateinit var subject: Host

  override fun setupSubject(application: Application) {
    val applicationComponent =
        DaggerPassedApplicationComponent.builder().binding(application).build()
    subject = HostImpl(applicationComponent = applicationComponent, coroutines = coroutines)
  }

  override fun subject(): Host = subject
}
