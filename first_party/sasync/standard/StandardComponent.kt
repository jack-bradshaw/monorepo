package com.jackbradshaw.sasync.standard

import com.jackbradshaw.sasync.inbound.transport.InboundTransport
import com.jackbradshaw.sasync.outbound.transport.OutboundTransport
import com.jackbradshaw.sasync.standard.error.StandardError
import com.jackbradshaw.sasync.standard.input.StandardInput
import com.jackbradshaw.sasync.standard.output.StandardOutput

interface StandardComponent {
  @StandardInput fun standardInputInboundTransport(): InboundTransport

  @StandardOutput fun standardOutputOutboundTransport(): OutboundTransport

  @StandardError fun standardErrorOutboundTransport(): OutboundTransport
}
