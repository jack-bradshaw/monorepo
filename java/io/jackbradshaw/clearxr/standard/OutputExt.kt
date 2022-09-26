package io.jackbradshaw.clearxr.standard

import io.jackbradshaw.clearxr.model.output

private fun output(
    user: StandardUser,
    identifier: StandardOutputIdentifier,
    location: StandardOutputLocation? = null
) = output(user.user, identifier.identifier, location?.location)