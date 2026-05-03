package com.jackbradshaw.oksp.testing.application.chassis

import com.jackbradshaw.kale.model.Result
import com.jackbradshaw.kale.model.Source
import com.jackbradshaw.oksp.application.Application

interface ApplicationChassis {
  suspend fun run(application: Application, sources: Set<Source> = emptySet()): Result
}
