package com.jackbradshaw.oksp.application

import javax.inject.Inject
import java.util.ServiceLoader

class ApplicationLoaderImpl @Inject constructor() : ApplicationLoader {
  override fun load(): Application {
    val loader = ServiceLoader.load(Application::class.java, ApplicationLoaderImpl::class.java.classLoader)
    val iterator = loader.iterator()
    require (iterator.hasNext()) {
      "No Application implementation found. Please ensure your artefact exports a service implementation in META-INF/services/com.jackbradshaw.oksp.application.Application"
    }
    val application = iterator.next()
    require(!iterator.hasNext()) {
      "Multiple Application implementations found. Only one application per artefact is supported."
    }
    return application
  }
}