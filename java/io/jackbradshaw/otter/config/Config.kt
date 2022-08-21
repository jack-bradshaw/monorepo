package io.jackbradshaw.otter.config

data class Config(
  val vrEnabled: Boolean,
  val headlessEnabled: Boolean,
  val gammaCorrectionEnabled: Boolean = true
)