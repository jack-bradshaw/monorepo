package io.jackbradshaw.otter.physics.collisions

fun interaction(
        combinedCoefficientOfRestitution: Float,
        combinedCoefficientOfFriction: Float,
        appliedImpulse: Float
) = Interaction.newBuilder()
            .setCombinedCoefficientOfRestitution(combinedCoefficientOfRestitution)
            .setCombinedCoefficientOfFriction(combinedCoefficientOfFriction)
            .setAppliedImpulse(appliedImpulse)
            .build()