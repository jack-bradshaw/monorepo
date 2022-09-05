package io.jackbradshaw.otter.openxr.model


fun interactionProfile(vendor: String, controller: String, inputs: Set<Input>, outputs: Set<Output>) =
    InteractionProfile.newBuilder()
        .setVendor(Vendor.newBuilder().setStandardName(vendor).build())
        .setController(Controller.newBuilder().setStandardName(controller).build())
        .addAllInput(inputs)
        .addAllOutput(outputs)
        .build()

//fun InteractionProfile.path(): String = "/interaction_profiles/${vendor.standardName}/${controller.standardName}"