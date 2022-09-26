package io.jackbradshaw.otter.openxr.standard

import io.jackbradshaw.otter.openxr.model.output

/**
 * Creates an [io.jackbradshaw.otter.openxr.model.output] from standard types.
 */
fun output(
    user: StandardUser,
    identifier: StandardOutputIdentifier,
    location: StandardOutputLocation? = null
) = output(user.user, identifier.identifier, location?.location)