package com.jackbradshaw.sasync.inbound

import com.jackbradshaw.sasync.inbound.transport.InboundTransport

interface InboundComponent {
  fun inboundTransportFactory(): InboundTransport.Factory
}
