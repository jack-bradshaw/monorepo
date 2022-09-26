package io.jackbradshaw.otter.openxr.standard

import io.jackbradshaw.otter.openxr.model.input

/**
 * Creates an [io.jackbradshaw.otter.openxr.model.input] from standard types.
 */
fun input(
    user: StandardUser,
    identifier: StandardInputIdentifier,
    component: StandardInputComponent,
    location: StandardInputLocation? = null) = input(user.user, identifier.identifier, component.component, location?.location)

