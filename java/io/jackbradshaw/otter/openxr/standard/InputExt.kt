package io.jackbradshaw.otter.openxr.standard

import io.jackbradshaw.otter.openxr.model.Input

/**
 * Creates an [io.jackbradshaw.otter.openxr.model.input] from standard types.
 */
fun input(
    user: StandardUser,
    identifier: StandardInputIdentifier,
    component: StandardInputComponent,
    location: StandardInputLocation? = null
) = Input
    .newBuilder()
    .apply {
      setUser(user.user)
      setIdentifier(identifier.identifier)
      setComponent(component.component)
      location?.let { setLocation(it.location) }
    }
    .build()

