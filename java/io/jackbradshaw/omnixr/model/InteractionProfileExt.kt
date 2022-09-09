package io.jackbradshaw.omnixr.model


fun interactionProfile(vendorId: String, controllerId: String, inputs: Set<Input> = setOf(), outputs: Set<Output> = setOf()) =
    InteractionProfile.newBuilder()
        .setVendor(Vendor.newBuilder().setId(vendorId).build())
        .setController(Controller.newBuilder().setId(controllerId).build())
        .addAllInput(inputs)
        .addAllOutput(outputs)
        .build()