package com.jackbradshaw.sasync

import com.jackbradshaw.concurrency.Concurrency
import com.jackbradshaw.concurrency.DaggerConcurrency
import com.jackbradshaw.coroutines.Coroutines
import com.jackbradshaw.coroutines.DaggerCoroutines
import com.jackbradshaw.sasync.config.Config
import com.jackbradshaw.sasync.config.defaultConfig
import com.jackbradshaw.sasync.standard.error.StandardError
import com.jackbradshaw.sasync.standard.error.StandardErrorModule
import com.jackbradshaw.sasync.standard.input.StandardInput
import com.jackbradshaw.sasync.standard.input.StandardInputModule
import com.jackbradshaw.sasync.standard.output.StandardOutput
import com.jackbradshaw.sasync.standard.output.StandardOutputModule
import com.jackbradshaw.sasync.transport.config.TransportConfigModule
import com.jackbradshaw.sasync.transport.inbound.Inbound
import com.jackbradshaw.sasync.transport.outbound.Outbound
import dagger.BindsInstance
import dagger.Component
import java.io.InputStream
import java.io.OutputStream

@SasyncScope
@Component(
    modules =
        [
            StandardInputModule::class,
            StandardOutputModule::class,
            StandardErrorModule::class,
            TransportConfigModule::class,
        ],
    dependencies = [Concurrency::class, Coroutines::class])
interface Sasync {

  @StandardInput fun standardInput(): Inbound

  @StandardOutput fun standardOutput(): Outbound

  @StandardError fun standardError(): Outbound

  @Component.Builder
  interface Builder {

    @BindsInstance fun setConfig(config: Config): Builder

    @BindsInstance fun setStandardInput(@StandardInput stream: InputStream): Builder

    @BindsInstance fun setStandardOutput(@StandardOutput stream: OutputStream): Builder

    @BindsInstance fun setStandardError(@StandardError stream: OutputStream): Builder

    fun setConcurrency(concurrency: Concurrency): Builder

    fun setCoroutines(coroutines: Coroutines): Builder

    fun build(): Sasync
  }
}

fun sasync(
    config: Config = defaultConfig,
    concurrency: Concurrency = DaggerConcurrency.create(),
    coroutines: Coroutines = DaggerCoroutines.create(),
    standardInput: InputStream = System.`in`,
    standardOutput: OutputStream = System.out,
    standardError: OutputStream = System.err
) =
    DaggerSasync.builder()
        .setConfig(config)
        .setConcurrency(concurrency)
        .setCoroutines(coroutines)
        .setStandardInput(standardInput)
        .setStandardOutput(standardOutput)
        .setStandardError(standardError)
        .build()
