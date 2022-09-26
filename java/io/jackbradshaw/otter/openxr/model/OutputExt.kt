package io.jackbradshaw.otter.openxr.model

fun output(
    user: User,
    identifier: OutputIdentifier,
    location: OutputLocation? = null
) = Output.newBuilder().apply {
  setUser(user)
  setIdentifier(identifier)
  if (location != null) setLocation(location)
}.build()