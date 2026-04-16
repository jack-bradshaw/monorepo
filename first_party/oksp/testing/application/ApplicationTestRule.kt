package com.jackbradshaw.oksp.testing.application

import com.jackbradshaw.kale.ksprunner.JvmSource
import com.jackbradshaw.kale.ksprunner.KspRunner
import com.jackbradshaw.oksp.application.Application
import org.junit.rules.TestRule

interface ApplicationTestRule : TestRule {
  fun initialize(application: Application, sources: List<JvmSource>)

  val result: KspRunner.Result?
}
