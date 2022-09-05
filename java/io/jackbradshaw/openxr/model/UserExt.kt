package io.jackbradshaw.openxr.model

fun user(name: String) = User.newBuilder().setStandardName(name).build()

fun User.path() = "user/" + standardName