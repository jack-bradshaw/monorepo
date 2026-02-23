package com.jackbradshaw.oksp.application

import javax.inject.Inject

class ApplicationLoaderImpl @Inject constructor() : ApplicationLoader {
  override fun load(): Application {
    // TODO load from META-INF
    throw NotImplementedError("To be implemented")
  }
}