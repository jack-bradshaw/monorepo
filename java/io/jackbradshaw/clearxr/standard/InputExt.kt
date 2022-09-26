package io.jackbradshaw.clearxr.standard

import io.jackbradshaw.clearxr.model.input

/**
 * Creates an [io.jackbradshaw.clearxr.model.input] from standard types.
 */
private fun input(
    user: StandardUser,
    identifier: StandardInputIdentifier,
    component: StandardInputComponent,
    location: StandardInputLocation? = null) = input(user.user, identifier.identifier, component.component, location?.location)

