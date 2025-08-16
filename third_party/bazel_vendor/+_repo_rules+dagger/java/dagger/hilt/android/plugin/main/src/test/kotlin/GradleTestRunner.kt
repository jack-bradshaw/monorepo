/*
 * Copyright (C) 2020 The Dagger Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.BuildTask
import org.gradle.testkit.runner.GradleRunner
import org.junit.rules.TemporaryFolder

/** Testing utility class that sets up a simple Android project that applies the Hilt plugin. */
class GradleTestRunner(val tempFolder: TemporaryFolder) {
  private val pluginClasspaths = mutableListOf<String>()
  private val pluginIds = mutableListOf<String>()
  private val dependencies = mutableListOf<String>()
  private val activities = mutableListOf<String>()
  private val additionalAndroidOptions = mutableListOf<String>()
  private val hiltOptions = mutableListOf<String>()
  private val additionalClosures = mutableListOf<String>()
  private var appClassName: String? = null
  private var buildFile: File? = null
  private var gradlePropertiesFile: File? = null
  private var manifestFile: File? = null
  private var additionalTasks = mutableListOf<String>()
  private var isAppProject: Boolean = true

  init {
    tempFolder.newFolder("src", "main", "java", "minimal")
    tempFolder.newFolder("src", "test", "java", "minimal")
    tempFolder.newFolder("src", "main", "res")
  }

  // Adds a Gradle plugin classpath to the test project,
  // e.g. "org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0"
  fun addPluginClasspath(pluginClasspath: String) {
    pluginClasspaths.add(pluginClasspath)
  }

  // Adds a Gradle plugin id to the test project, e.g. "kotlin-android"
  fun addPluginId(pluginId: String) {
    pluginIds.add(pluginId)
  }

  // Adds project dependencies, e.g. "implementation <group>:<id>:<version>"
  fun addDependencies(vararg deps: String) {
    dependencies.addAll(deps)
  }

  // Adds an <activity> tag in the project's Android Manifest, e.g. "<activity name=".Foo"/>
  fun addActivities(vararg activityElements: String) {
    activities.addAll(activityElements)
  }

  // Adds 'android' options to the project's build.gradle, e.g. "lintOptions.checkReleaseBuilds =
  // false"
  fun addAndroidOption(vararg options: String) {
    additionalAndroidOptions.addAll(options)
  }

  // Adds 'hilt' options to the project's build.gradle, e.g. "enableExperimentalClasspathAggregation
  // = true"
  fun addHiltOption(vararg options: String) {
    hiltOptions.addAll(options)
  }

  fun addAdditionalClosure(closure: String) {
    additionalClosures.add(closure)
  }

  // Adds a source package to the project. The package path is relative to 'src/main/java'.
  fun addSrcPackage(packagePath: String) {
    File(tempFolder.root, "src/main/java/$packagePath").mkdirs()
  }

  // Adds a source file to the project. The source path is relative to 'src/main/java'.
  fun addSrc(srcPath: String, srcContent: String): File {
    File(tempFolder.root, "src/main/java/${srcPath.substringBeforeLast(File.separator)}").mkdirs()
    return tempFolder.newFile("/src/main/java/$srcPath").apply { writeText(srcContent) }
  }

  // Adds a test source file to the project. The source path is relative to 'src/test/java'.
  fun addTestSrc(srcPath: String, srcContent: String): File {
    File(tempFolder.root, "src/test/java/${srcPath.substringBeforeLast(File.separator)}").mkdirs()
    return tempFolder.newFile("/src/test/java/$srcPath").apply { writeText(srcContent) }
  }

  // Adds a resource file to the project. The source path is relative to 'src/main/res'.
  fun addRes(resPath: String, resContent: String): File {
    File(tempFolder.root, "src/main/res/${resPath.substringBeforeLast(File.separator)}").mkdirs()
    return tempFolder.newFile("/src/main/res/$resPath").apply { writeText(resContent) }
  }

  fun setAppClassName(name: String) {
    appClassName = name
  }

  fun setIsAppProject(flag: Boolean) {
    isAppProject = flag
  }

  fun runAdditionalTasks(taskName: String) {
    additionalTasks.add(taskName)
  }

  // Executes a Gradle builds and expects it to succeed.
  fun build(): Result {
    setupFiles()
    return Result(tempFolder.root, createRunner().build())
  }

  // Executes a Gradle build and expects it to fail.
  fun buildAndFail(): Result {
    setupFiles()
    return Result(tempFolder.root, createRunner().buildAndFail())
  }

  private fun setupFiles() {
    writeBuildFile()
    writeGradleProperties()
    writeAndroidManifest()
  }

  private fun writeBuildFile() {
    buildFile?.delete()
    buildFile =
      tempFolder.newFile("build.gradle").apply {
        writeText(
          """
        buildscript {
          repositories {
            google()
            mavenCentral()
          }
          dependencies {
            classpath 'com.android.tools.build:gradle:7.1.2'
            ${pluginClasspaths.joinToString(separator = "\n") { "classpath '$it'" }}
          }
        }

        plugins {
          id '${ if (isAppProject) "com.android.application" else "com.android.library" }'
          id 'com.google.dagger.hilt.android'
          ${pluginIds.joinToString(separator = "\n") { "id '$it'" }}
        }

        android {
          compileSdkVersion 33
          buildToolsVersion "33.0.1"

          defaultConfig {
            ${ if (isAppProject) "applicationId \"plugin.test\"" else "" }
            minSdkVersion 21
            targetSdkVersion 33
          }

          namespace = "minimal"

          compileOptions {
              sourceCompatibility JavaVersion.VERSION_11
              targetCompatibility JavaVersion.VERSION_11
          }
          ${additionalAndroidOptions.joinToString(separator = "\n")}
        }

        allprojects {
          repositories {
            mavenLocal()
            google()
            mavenCentral()
          }
        }

        dependencies {
          implementation(platform('org.jetbrains.kotlin:kotlin-bom:1.8.0'))
          ${dependencies.joinToString(separator = "\n")}
        }

        hilt {
          ${hiltOptions.joinToString(separator = "\n")}
        }
        ${additionalClosures.joinToString(separator = "\n")}
        """
            .trimIndent()
        )
      }
  }

  private fun writeGradleProperties() {
    gradlePropertiesFile?.delete()
    gradlePropertiesFile =
      tempFolder.newFile("gradle.properties").apply {
        writeText(
          """
        android.useAndroidX=true
        // TODO(b/296583777): See if there's a better way to fix the OOM error.
        org.gradle.jvmargs=-XX:MaxMetaspaceSize=1g
        """
            .trimIndent()
        )
      }
  }

  private fun writeAndroidManifest() {
    manifestFile?.delete()
    manifestFile =
      tempFolder.newFile("/src/main/AndroidManifest.xml").apply {
        writeText(
          """
        <?xml version="1.0" encoding="utf-8"?>
        <manifest xmlns:android="http://schemas.android.com/apk/res/android" package="minimal">
            <application
                android:name="${appClassName ?: "android.app.Application"}"
                android:theme="@style/Theme.AppCompat.Light.DarkActionBar">
                ${activities.joinToString(separator = "\n")}
            </application>
        </manifest>
        """
            .trimIndent()
        )
      }
  }

  private fun createRunner() =
    GradleRunner.create()
      .withProjectDir(tempFolder.root)
      .withArguments(listOf("--stacktrace", "assembleDebug") + additionalTasks)
      .withPluginClasspath()
      //    .withDebug(true) // Add this line to enable attaching a debugger to the gradle test
      // invocation
      .forwardOutput()

  // Data class representing a Gradle Test run result.
  data class Result(private val projectRoot: File, private val buildResult: BuildResult) {

    val tasks: List<BuildTask>
      get() = buildResult.tasks

    // Finds a task by name.
    fun getTask(name: String) = buildResult.task(name) ?: error("Task '$name' not found.")

    // Gets the full build output.
    fun getOutput() = buildResult.output

    // Finds a transformed file. The srcFilePath is relative to the app's package.
    fun getTransformedFile(srcFilePath: String): File {
      val parentDir =
        File(projectRoot, "build/intermediates/classes/debug/transformDebugClassesWithAsm/dirs")
      return File(parentDir, srcFilePath).also {
        if (!it.exists()) {
          error("Unable to find transformed class ${it.path}")
        }
      }
    }
  }
}
