package io.jackbradshaw.otter.openxr.installation

import io.jackbradshaw.otter.openxr.encoding.EncodingImpl
import io.jackbradshaw.otter.openxr.manifest.ManifestGeneratorImpl
import kotlinx.coroutines.runBlocking

class MainKt {
  fun main() = runBlocking {
    val manifests = ManifestGeneratorImpl(EncodingImpl()).generateManifests()
    println(manifests.primaryManifest)
    for ((x, y) in manifests.secondaryManifests) {
      println(y)
    }
  }
}