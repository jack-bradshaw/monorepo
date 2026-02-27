package com.jackbradshaw.backstab.ksp.repository

import com.jackbradshaw.backstab.core.repository.Repository as CoreRepository

interface Repository {
  suspend fun run()
}