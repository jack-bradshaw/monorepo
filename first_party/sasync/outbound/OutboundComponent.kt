package com.jackbradshaw.sasync.outbound

import com.jackbradshaw.sasync.outbound.transport.OutboundTransport

interface OutboundComponent {
  fun outboundTransportFactory(): OutboundTransport.Factory
}
