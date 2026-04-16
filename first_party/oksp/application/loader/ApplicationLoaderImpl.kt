package com.jackbradshaw.oksp.application.loader

import com.jackbradshaw.oksp.application.Application
import java.util.ServiceLoader
import javax.inject.Inject

class ApplicationLoaderImpl @Inject constructor(private val classLoader: ClassLoader) :
    ApplicationLoader {

  override fun load(): Application {
    val loader = ServiceLoader.load(Application::class.java, classLoader)
    val iterator = loader.iterator()
    require(iterator.hasNext()) {
      "No Application implementation found. " +
          "Ensure this JAR contains an application declartion in " +
          "META-INF/services/com.jackbradshaw.oksp.application.Application"
    }
    val application = iterator.next()
    require(!iterator.hasNext()) {
      "Multiple Application implementations found. " +
          "Ensure this JAR contains exactly one application declartion in " +
          "META-INF/services/com.jackbradshaw.oksp.application.Application"
    }
    return application
  }
}
