package com.jackbradshaw.sasync.transport.config

val defaultConfig = config {
  inboundConfig = com.jackbradshaw.sasync.transport.inbound.config.defaultConfig
  outboundConfig = com.jackbradshaw.sasync.transport.outbound.config.defaultConfig
}
