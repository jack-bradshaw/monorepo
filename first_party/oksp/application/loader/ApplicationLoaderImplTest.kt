package com.jackbradshaw.oksp.application.loader

import com.jackbradshaw.oksp.application.Application
import com.jackbradshaw.oksp.application.ApplicationComponent
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.net.URLClassLoader
import java.io.File
import java.nio.file.Files

@RunWith(JUnit4::class)
class ApplicationLoaderImplTest : ApplicationLoaderTest() {

  private lateinit var impl: ApplicationLoaderImpl
  private lateinit var currentClassLoader: ClassLoader
  private var tempDir: File? = null

  override fun subject(): ApplicationLoader = impl

  override fun setupSubject(state: DeclarationState) {
    impl = ApplicationLoaderImpl()
    tempDir?.deleteRecursively()
    
    val root = Files.createTempDirectory("application_test").toFile()
    tempDir = root
    val servicesDir = File(root, "META-INF/services")
    servicesDir.mkdirs()
    val serviceFile = File(servicesDir, "com.jackbradshaw.oksp.application.Application")

    when (state) {
      DeclarationState.NOT_DECLARED -> {
        // Do nothing
      }
      DeclarationState.DECLARED_ONCE -> {
        serviceFile.writeText("com.jackbradshaw.oksp.application.loader.TestApp1\n")
      }
      DeclarationState.DECLARED_REPEATEDLY -> {
        serviceFile.writeText("com.jackbradshaw.oksp.application.loader.TestApp1\ncom.jackbradshaw.oksp.application.loader.TestApp2\n")
      }
    }

    currentClassLoader = URLClassLoader(arrayOf(root.toURI().toURL()), this::class.java.classLoader)
    impl.classLoaderOverride = currentClassLoader
  }
}

class TestApp1 : Application {
  override suspend fun onCreate(component: ApplicationComponent) {}
  override suspend fun onDestroy() {}
}

class TestApp2 : Application {
  override suspend fun onCreate(component: ApplicationComponent) {}
  override suspend fun onDestroy() {}
}
