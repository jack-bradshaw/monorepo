package com.jackbradshaw.kale.model

/** The files produced by a KSP run. */
data class Artifacts(
    /** Generated Kotlin source files. */
    val kotlinSources: List<Source>,

    /** Generated Java source files. */
    val javaSources: List<Source>,

    /** All other generated files. */
    val resources: List<Resource>
) {
  companion object {
    /** Creates [Artifacts] with no generated sources or resources. */
    fun createEmpty() =
        Artifacts(kotlinSources = emptyList(), javaSources = emptyList(), resources = emptyList())
  }
}
