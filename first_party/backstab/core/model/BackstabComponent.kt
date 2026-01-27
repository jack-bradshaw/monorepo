package com.jackbradshaw.backstab.core.model

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.TypeName

/**
 * Represents the parsed metadata of a Dagger component annotated with @Backstab.
 *
 * This model serves as the intermediate representation between the KSP AST and the
 * code generation logic, decoupling the two.
 */
data class BackstabComponent(
    val packageName: String,
    val name: String,
    val builder: Builder?
) {
    /**
     * Represents the @Component.Builder or @Component.Factory interface.
     */
    data class Builder(
        val bindings: List<BuilderMethod>
    )

    /**
     * Represents a single method on the component's @Component.Builder interface.
     *
     * Each instance corresponds to a dependency that needs to be supplied to the component
     * via a builder method call.
     */
    data class BuilderMethod(
        val methodName: String,
        val paramType: TypeName,
        /**
         * The @Named qualifier, if present.
         * Dagger treats @Named specially, so we promote it to a first-class property.
         */
        val named: AnnotationSpec? = null,
        
        /**
         * Other qualifiers on the parameter (custom annotations meta-annotated with @Qualifier).
         */
        val qualifiers: List<AnnotationSpec> = emptyList()
    )
}
