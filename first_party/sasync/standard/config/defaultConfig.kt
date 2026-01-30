package com.jackbradshaw.sasync.standard.config

val defaultConfig = config {
  inboundConfig = com.jackbradshaw.sasync.inbound.config.defaultConfig
  outboundConfig = com.jackbradshaw.sasync.outbound.config.defaultConfig
}
