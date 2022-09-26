package io.jackbradshaw.clearxr.standard

import io.jackbradshaw.clearxr.model.output

/**
 * Creates an [io.jackbradshaw.clearxr.model.output] from standard types.
 */
fun output(
    user: StandardUser,
    identifier: StandardOutputIdentifier,
    location: StandardOutputLocation? = null
) = output(user.user, identifier.identifier, location?.location)