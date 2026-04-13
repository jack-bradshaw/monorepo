package com.jackbradshaw.oksp.application.loader

import javax.inject.Inject
import java.util.ServiceLoader
import com.jackbradshaw.oksp.application.Application

class ApplicationLoaderImpl @Inject constructor() : ApplicationLoader {

  var classLoaderOverride: ClassLoader? = null

  override fun load(): Application {
    val cl = classLoaderOverride ?: ApplicationLoaderImpl::class.java.classLoader
    val loader = ServiceLoader.load(Application::class.java, cl)
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