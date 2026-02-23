package com.jackbradshaw.oksp.services

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface LifecycleService {
  val stage: StateFlow<Stage>
}

enum class Stage {
  PENDING,
  RUNNING,
  FINISHED
}