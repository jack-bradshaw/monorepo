package io.jackbradshaw.otter.openxr.model


fun interactionProfile(vendor: String, controller: String, inputs: Set<InputSpec> = setOf(), outputs: Set<OutputSpec> = setOf()) =
    InteractionProfile.newBuilder()
        .setVendor(Vendor.newBuilder().setStandardName(vendor).build())
        .setController(Controller.newBuilder().setStandardName(controller).build())
        .addAllInputSpec(inputs)
        .addAllOutputSpec(outputs).build()

fun InteractionProfile.path(): String = "/interaction_profiles/${vendor.standardName}/${controller.standardName}"