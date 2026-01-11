package com.jackbradshaw.sasync.standard

import com.jackbradshaw.sasync.inbound.InboundComponent
import com.jackbradshaw.sasync.inbound.inboundComponent
import com.jackbradshaw.sasync.inbound.transport.InboundTransport
import com.jackbradshaw.sasync.outbound.OutboundComponent
import com.jackbradshaw.sasync.outbound.outboundComponent
import com.jackbradshaw.sasync.outbound.transport.OutboundTransport
import com.jackbradshaw.sasync.standard.config.Config
import com.jackbradshaw.sasync.standard.config.ConfigModule
import com.jackbradshaw.sasync.standard.config.defaultConfig
import com.jackbradshaw.sasync.standard.error.StandardError
import com.jackbradshaw.sasync.standard.error.StandardErrorModule
import com.jackbradshaw.sasync.standard.input.StandardInput
import com.jackbradshaw.sasync.standard.input.StandardInputModule
import com.jackbradshaw.sasync.standard.output.StandardOutput
import com.jackbradshaw.sasync.standard.output.StandardOutputModule
import dagger.BindsInstance
import dagger.Component
import java.io.InputStream
import java.io.OutputStream

interface StandardComponent {

  @StandardInput fun standardInput(): InboundTransport

  @StandardOutput fun standardOutput(): OutboundTransport

  @StandardError fun standardError(): OutboundTransport
}

@StandardScope
@Component(
    modules =
        [
            StandardInputModule::class,
            StandardOutputModule::class,
            StandardErrorModule::class,
            ConfigModule::class,
        ],
    dependencies = [InboundComponent::class, OutboundComponent::class])
interface ProdStandardComponent : StandardComponent {

  @Component.Builder
  interface Builder {

    @BindsInstance fun binding(config: Config): Builder

    @BindsInstance fun bindingStandardInput(@StandardInput stream: InputStream): Builder

    @BindsInstance fun bindingStandardOutput(@StandardOutput stream: OutputStream): Builder

    @BindsInstance fun bindingStandardError(@StandardError stream: OutputStream): Builder

    fun consuming(inbound: InboundComponent): Builder

    fun consuming(outbound: OutboundComponent): Builder

    fun build(): ProdStandardComponent
  }
}

fun standardComponent(
    config: Config = defaultConfig,
    inbound: InboundComponent = inboundComponent(),
    outbound: OutboundComponent = outboundComponent(),
    input: InputStream = System.`in`,
    output: OutputStream = System.out,
    error: OutputStream = System.err
) =
    DaggerProdStandardComponent.builder()
        .binding(config)
        .consuming(inbound)
        .consuming(outbound)
        .bindingStandardInput(input)
        .bindingStandardOutput(output)
        .bindingStandardError(error)
        .build()
