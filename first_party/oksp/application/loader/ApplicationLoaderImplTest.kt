package com.jackbradshaw.oksp.application.loader

import dagger.BindsInstance
import dagger.Component
import java.io.File
import java.net.URLClassLoader
import java.nio.file.Files
import javax.inject.Inject
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ApplicationLoaderImplTest : ApplicationLoaderTest() {

  @Inject lateinit var _subject: ApplicationLoaderImpl

  override fun subject(): ApplicationLoader = _subject

  override fun setupSubject(applications: List<Class<*>>) {
    val workingDirectory = Files.createTempDirectory("application_test").toFile()
    val servicesDir = File(workingDirectory, "META-INF/services").also { it.mkdirs() }
    val serviceManifestFile = File(servicesDir, "com.jackbradshaw.oksp.application.Application")

    for (application in applications) {
      serviceManifestFile.appendText("${application.name}\n")
    }

    DaggerApplicationLoaderImplTest_TestComponent.factory()
        .create(
            URLClassLoader(arrayOf(workingDirectory.toURI().toURL()), this::class.java.classLoader))
        .inject(this)
  }

  @Component
  interface TestComponent {
    fun inject(target: ApplicationLoaderImplTest)

    @Component.Factory
    interface Factory {
      fun create(@BindsInstance classLoader: ClassLoader): TestComponent
    }
  }
}
