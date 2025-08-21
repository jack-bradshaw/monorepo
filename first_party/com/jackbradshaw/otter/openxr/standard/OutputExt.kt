package com.jackbradshaw.otter.openxr.standard

import com.jackbradshaw.otter.openxr.model.Output

/** Creates an [com.jackbradshaw.otter.openxr.model.output] from standard types. */
fun output(
    user: StandardUser,
    identifier: StandardOutputIdentifier,
    location: StandardOutputLocation? = null
) =
    Output.newBuilder()
        .apply {
          setUser(user.user)
          setIdentifier(identifier.identifier)
          location?.let { setLocation(it.location) }
        }
        .build()
