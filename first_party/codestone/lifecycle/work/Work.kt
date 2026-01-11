package com.jackbradshaw.codestone.lifecycle.work

import kotlin.reflect.KType

interface Work<out T> {
  val handle: T
  val workType: KType
}
