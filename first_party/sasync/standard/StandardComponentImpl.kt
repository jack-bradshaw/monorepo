package com.jackbradshaw.sasync.standard

import com.jackbradshaw.sasync.inbound.InboundComponent
import com.jackbradshaw.sasync.outbound.OutboundComponent
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

@StandardScope
@Component(
    dependencies = [InboundComponent::class, OutboundComponent::class],
    modules = [StandardInputModule::class, StandardOutputModule::class, StandardErrorModule::class])
interface StandardComponentImpl : StandardComponent {
  @Component.Builder
  interface Builder {
    fun consuming(component: InboundComponent): Builder

    fun consuming(component: OutboundComponent): Builder

    @BindsInstance fun bindingStandardInput(@StandardInput stream: InputStream): Builder

    @BindsInstance fun bindingStandardOutput(@StandardOutput stream: OutputStream): Builder

    @BindsInstance fun bindingStandardError(@StandardError stream: OutputStream): Builder

    fun build(): StandardComponentImpl
  }
}

fun standardComponent(
    inbound: InboundComponent,
    outbound: OutboundComponent,
    input: InputStream = System.`in`,
    output: OutputStream = System.`out`,
    error: OutputStream = System.err
): StandardComponent =
    DaggerStandardComponentImpl.builder()
        .consuming(inbound)
        .consuming(outbound)
        .bindingStandardInput(input)
        .bindingStandardOutput(output)
        .bindingStandardError(error)
        .build()
