package io.jackbradshaw.otter.openxr.model

fun inputIdentifier(name: String) = InputIdentifier.newBuilder().setStandardName(name).build()