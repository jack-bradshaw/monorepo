package io.jackbradshaw.omnixr.model

fun output(
    user: User,
    identifier: OutputIdentifier,
    location: OutputLocation? = null
) = Output.newBuilder().apply {
  setUser(user)
  setIdentifier(identifier)
  if (location != null) setLocation(location)
}.build()

//fun OutputSpec.path() = "output/" + identifier.standardName + (location?.standardName?.let { "_$it" } ?: "")