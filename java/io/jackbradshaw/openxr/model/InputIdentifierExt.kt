package io.jackbradshaw.openxr.model

fun inputIdentifier(name: String) = InputIdentifier.newBuilder().setStandardName(name).build()