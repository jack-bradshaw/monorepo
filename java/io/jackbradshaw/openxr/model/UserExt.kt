package io.jackbradshaw.otter.openxr.model

fun user(name: String) = User.newBuilder().setStandardName(name).build()

fun User.path() = "user/" + standardName