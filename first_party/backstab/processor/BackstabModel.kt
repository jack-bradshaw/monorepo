package com.jackbradshaw.backstab.processor

import com.squareup.kotlinpoet.TypeName

/**
 * Represents the parsed metadata of a Dagger component annotated with @Backstab.
 *
 * This model serves as the intermediate representation between the KSP AST and the
 * code generation logic, decoupling the two.
 */
data class BackstabComponent(
    val packageName: String,
    val simpleName: String,
    val builderBindings: List<ComponentBuilderMethod>
) {
    /**
     * Represents a single method on the component's @Component.Builder interface.
     *
     * Each instance corresponds to a dependency that needs to be supplied to the component
     * via a builder method call.
     */
    data class ComponentBuilderMethod(
        val methodName: String,
        val paramName: String,
        val paramType: TypeName
    )
}