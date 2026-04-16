package com.jackbradshaw.kale.model

/** The outputs of a KSP run. */
data class KspArtefacts(
    /** Generated Kotlin source files. */
    val kotlinSources: List<JvmSource>,

    /** Generated Java source files. */
    val javaSources: List<JvmSource>,

    /** Generated compiled class files. */
    val classes: List<JvmClass>,

    /** All other generated files. */
    val misc: List<JvmSource>
) {
  companion object {
    /** Creates [KspArtefacts] with all properties set to empty. */
    fun createEmpty() =
        KspArtefacts(
            kotlinSources = emptyList(),
            javaSources = emptyList(),
            misc = emptyList(),
            classes = emptyList())
  }
}
