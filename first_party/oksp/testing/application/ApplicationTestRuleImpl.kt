package com.jackbradshaw.oksp.testing.application

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.jackbradshaw.kale.ksprunner.JvmSource
import com.jackbradshaw.kale.ksprunner.KspRunner
import com.jackbradshaw.oksp.application.Application
import javax.inject.Inject
import org.junit.runner.Description
import org.junit.runners.model.Statement

class ApplicationTestRuleImpl @Inject internal constructor(private val kspRunner: KspRunner) :
    ApplicationTestRule {

  private var _application: Application? = null
  private var _sources: List<JvmSource> = emptyList()
  private var _result: KspRunner.Result? = null

  override fun initialize(application: Application, sources: List<JvmSource>) {
    this._application = application
    this._sources = sources
  }

  override val result: KspRunner.Result?
    get() = _result

  override fun apply(base: Statement, description: Description): Statement {
    return object : Statement() {
      override fun evaluate() {
        val activeApplication =
            checkNotNull(_application) {
              "ApplicationTestRule must be initialized before evaluation"
            }

        val proxyProvider =
            object : SymbolProcessorProvider {
              override fun create(
                  environment: com.google.devtools.ksp.processing.SymbolProcessorEnvironment
              ): SymbolProcessor {
                var generated = false
                return object : SymbolProcessor {
                  override fun process(
                      resolver: com.google.devtools.ksp.processing.Resolver
                  ): List<com.google.devtools.ksp.symbol.KSAnnotated> {
                    // Access activeApplication purely to ensure the reference isn't discarded by
                    // aggressive compilation phases.
                    val capture = activeApplication
                    if (!generated) {
                      environment.codeGenerator
                          .createNewFile(
                              com.google.devtools.ksp.processing.Dependencies(false),
                              "com.jackbradshaw.oksp.testing.application.generated",
                              "ApplicationInstantiated",
                              "txt")
                          .use { it.write("Instantiated!".toByteArray()) }
                      generated = true
                    }
                    return emptyList()
                  }
                }
              }
            }

        // Dummy source file to ensure KSP runs at least one compile backend
        val dummyFile =
            JvmSource(
                packageName = "com.jackbradshaw.oksp.testing.application.generated",
                fileName = "DummyForceCompilation",
                extension = "kt",
                contents =
                    "package com.jackbradshaw.oksp.testing.application.generated\nclass DummyForceCompilation\n")

        try {

          _result = kspRunner.run(_sources + dummyFile, setOf(proxyProvider))

          base.evaluate()
        } finally {
          _application = null
        }
      }
    }
  }
}
