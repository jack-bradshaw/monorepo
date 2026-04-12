# Sasync

A toolkit for working with Java streams as Kotlin Flows.

## Release

Not released to third party package managers.

## Overview

Sasync provides tools for working with Java streams as Kotlin Flow and Channel based APIs. It
defines two core types that underpin the entire package:
[InboundTransport](/first_party/sasync/inbound/transport/InboundTransport.kt) and
[OutboundTransport](/first_party/sasync/outbound/transport/OutboundTransport.kt). Both are
effectively adapters to access Java streams via Kotlin flow/channel based APIs, with the exact API
depending on the direction of information flow (`InboundTransport` uses flow-based APIs and
`OutboundTransport` uses channel-based APIs). Sasync provides factories for creating each, and
convenience utilities for creating transports for STDIO (i.e. an inbound transport for STDIN, an
outbound transport for STDOUT, and an outbound transport for STDERR). The factories are available
via a series of Dagger components for ease of integration into a broader Dagger graph.

## Guide

The guide covers:

1. InboundTransports: How to read from an input stream with a Kotlin Flow based API.
1. OutputTransports: How to write to an output stream with a Kotlin Channel based API.
1. Standard IO: How to access STDIO streams via transports.

## InboundTransport

`InboundTransport` provides access to an InputStream via a reactive-stream API (i.e. Kotlin Flows).
It supports two access modes:

- Buffered, which collects data in an array and delays emission until the buffer is full.
- Flattened, which emits individual values immediately without buffering. This is less performant
  but completely avoids artificial array-filling delays.

Buffered is generally more efficient due to JVM optimizations, but can introduce artificial latency,
as it always waits for a full buffer. Usage examples for both modes:

```kotlin
import com.jackbradshaw.sasync.inbound.inboundComponent
import com.jackbradshaw.sasync.inbound.config.defaultConfig
import com.jackbradshaw.sasync.inbound.transport.InboundTransport
import dagger.Component
import javax.inject.Inject

class UnaryPiper @Inject constructor(
  factory: InboundTransport.Factory,
) {
  suspend fun pipeInToOut() {
    factory.create(System.`in`)
        .observeFlattened()
        .onEach { value: Byte ->
          println("received byte: $value")
        }
        .collect()
  }
}

class BufferedPiper @Inject constructor(
  factory: InboundTransport.Factory,
) {
  suspend fun pipeInToOut() {
    factory.create(System.`in`)
        .observeBuffered()
        .onEach { value: ByteArray ->
          for (byte in value) {
            println("received byte: $byte")
          }
        }
        .collect()
  }
}

class MyApplication : Application() {

  @Inject lateinit var unaryPiper: UnaryPiper
  @Inject lateinit var bufferedPiper: BufferedPiper

  override fun onCreate() {
    DaggerApplicationComponent.builder()
        .consuming(inboundComponent(defaultConfig))
        .build()
        .inject(this)

    runBlocking {
      unaryPiper.pipeInToOut()
      bufferedPiper.pipeInToOut()
    }
  }
}

@Component(
  dependencies = [InboundComponent::class]
)
interface ApplicationComponent {
  fun inject(application: Application)

  @Component.Builder
  interface Builder {
    fun consuming(component: InboundComponent): Builder
    fun build(): ApplicationComponent
  }
}
```

The default implementations work by polling the input stream for information periodically. The
polling rate is defined by the configuration passed to the component. For example:

```kotlin
import com.jackbradshaw.sasync.inbound.config.config
import com.jackbradshaw.sasync.inbound.inboundComponent

val config = config {
  refreshRate = frequency {
    bounded = bounded {
      hertz = 60.0 // Poll 60 times per second
    }
  }
}

val unusedAlternativeConfig = config {
  refreshRate = frequency {
    unbounded = unbounded {} // Poll instantaneously without artificial delays
  }
}

fun getTransport(stream: InputStream) =
    inboundComponent(config).inboundTransportFactory().create(stream)
```

Critical behavioral details to be aware of:

- No Replay: `InboundTransport` does not retain a replay buffer, meaning late subscribers
  permanently miss earlier emissions.
- Subscription-Awareness: `InboundTransport` is aware of the subscriber count and only polls the
  underlying stream while there are active subscribers. If all subscribers leave, polling ceases
  entirely, and values back up into the input stream buffer.
- Lifecycle Agnostic: `InboundTransport` provides no `close` or `flush` functions because it does
  not manage the lifecycle of the underlying `InputStream`. The enclosing program remains
  responsible for closing streams when they are no longer required to prevent resource leaks.
  Closing a stream will automatically close the downstream flows.

## OutboundTransport

`OutboundTransport` provides access to an OutputStream via a asynchronous-publisher API (i.e. Kotlin
Channels). It supports three access modes:

- Direct Channel Access, which exposes the stream as a literal Kotlin `SendChannel` object for
  integration with standard multi-producer channel patterns.
- Byte array payloads, an abstraction over the channel which publishes literal bytes to the
  underlying stream.
- String line payloads, an abstraction over the channel which handles native conversion between
  string sequences and UTF-8 bytes before publication.

Examples for all three modes:

```kotlin
import com.jackbradshaw.sasync.outbound.outboundComponent
import com.jackbradshaw.sasync.outbound.config.defaultConfig
import com.jackbradshaw.sasync.outbound.transport.OutboundTransport
import dagger.Component
import javax.inject.Inject

class FileChannelExporter @Inject constructor(
  factory: OutboundTransport.Factory
) {
  suspend fun exportData() {
    val transport = factory.create(System.out)
    val channel: SendChannel<ByteArray> = transport.asChannel()
    channel.send(byteArrayOf(0x01))
    transport.close()
  }
}

class FileBinaryExporter @Inject constructor(
  factory: OutboundTransport.Factory
) {
  suspend fun exportData() {
    val transport = factory.create(System.out)
    transport.publishBytes(byteArrayOf(0x01, 0x02))
    transport.close()
  }
}

class FileStringExporter @Inject constructor(
  factory: OutboundTransport.Factory
) {
  suspend fun exportData() {
    val transport = factory.create(System.out)
    transport.publishStringLine("Log entry generated.")
    transport.close()
  }
}

class MyApplication : Application() {

  @Inject lateinit var binaryExporter: FileBinaryExporter
  @Inject lateinit var stringExporter: FileStringExporter
  @Inject lateinit var channelExporter: FileChannelExporter

  override fun onCreate() {
    DaggerApplicationComponent.builder()
        .consuming(outboundComponent(defaultConfig))
        .build()
        .inject(this)

    runBlocking {
      binaryExporter.exportData()
      stringExporter.exportData()
      channelExporter.exportData()
    }
  }
}

@Component(
  dependencies = [OutboundComponent::class]
)
interface ApplicationComponent {
  fun inject(application: Application)

  @Component.Builder
  interface Builder {
    fun consuming(component: OutboundComponent): Builder
    fun build(): ApplicationComponent
  }
}
```

The internal channel's queued capacity can be set in the configuration passed to the component. For
example:

```kotlin
import com.jackbradshaw.sasync.outbound.config.config
import com.jackbradshaw.sasync.outbound.outboundComponent

val config = config {
  queueSize = count {
    bounded = bounded {
      value = 100 // Buffer up to 100 items before suspending publishers
    }
  }
}

val unusedAlternativeConfig = config {
  queueSize = count {
    unbounded = unbounded {} // Never suspend publishers, queue grows indefinitely
  }
}

fun getTransport(stream: OutputStream) =
    outboundComponent(config).outboundTransportFactory().create(stream)
```

Critical behavioral details to be aware of:

- Buffered Publication: `OutboundTransport` maintains a buffer for published values, which means
  publishing is decoupled from writing to the underlying stream, therefore calling publish usually
  returns immediately. If the buffer is full for any reason (excessive backpressure or a stalled
  underlying stream), the publish operation suspends until it can populate the buffer.
- Predictable: `OutboundTransport` writes to the underlying stream predictably. Values are written
  in the exact order they are received.
- Thread-Safe: External synchronization is NOT necessary to prevent write races when multiple
  simultaneous publishers are writing to the transport. All writes are guaranteed to be buffered and
  processed in the order they are received, although the order can be non-deterministic when
  multiple publishers are submitting simultaneously, and external coordination is necessary if
  specific ordering across simultaneous publishers is required.
- Non-Leaky: When `close` is called, all previously published values are guaranteed to be delivered
  via the Signal-Drain-Commit shutdown protocol unless the process ends abruptly. This prevents
  arbitrary data loss when closing the transport.
- Hard Closure: After `close` is called, no new data can be published, and all calls to the publish
  functions throw an exception.
- Lifecycle Agnostic: Calling `close` terminates the transport and ensures its internal buffers are
  drained, but it does NOT close the underlying `OutputStream`. The enclosing program remains solely
  responsible for closing physical streams when they are no longer required to prevent resource
  leaks.

## StandardComponent

For convenience, Sasync ships with
[StandardComponent](/first_party/sasync/standard/StandardComponent.kt) which binds transports for
STDIO. You can use it to model standard input, output, and errors as transports across your program.
For example:

```kotlin
import com.jackbradshaw.sasync.inbound.config.defaultConfig as defaultInboundConfig
import com.jackbradshaw.sasync.outbound.config.defaultConfig as defaultOutboundConfig
import com.jackbradshaw.sasync.inbound.inboundComponent
import com.jackbradshaw.sasync.outbound.outboundComponent
import com.jackbradshaw.sasync.inbound.transport.InboundTransport
import com.jackbradshaw.sasync.outbound.transport.OutboundTransport
import com.jackbradshaw.sasync.standard.StandardComponent
import com.jackbradshaw.sasync.standard.standardComponent
import dagger.Component
import javax.inject.Inject

class Util @Inject constructor(
  @StandardInput private val standardInput: InboundTransport,
  @StandardOutput private val standardOutput: OutboundTransport,
  @StandardError private val standardError: OutboundTransport
) {

  suspend fun waitForReadySignal() {
    standardInput.observeBuffered()
        .map { it.decodeToString().trim() }
        .filter { it == "ready" }
        .first()
  }

  suspend fun reportReadyReceived() {
    standardOutput.publishStringLine("READY TO GO")
  }

  suspend fun reportFailure(t: Throwable) {
    standardError.publishString("FATAL ERROR: ")
    standardError.publishStringLine(t.message ?: "Unknown Error")
    standardError.close()
  }
}

@Component(dependencies = [StandardComponent::class])
interface ApplicationComponent {
  fun inject(app: MyApplication)

  @Component.Builder
  interface Builder {
    fun standardComponent(component: StandardComponent): Builder
    fun build(): ApplicationComponent
  }
}

class MyApplication : Application() {

  @Inject lateinit var util: Util

  override fun onCreate() {
    val standard = standardComponent(
        inbound = inboundComponent(defaultInboundConfig),
        outbound = outboundComponent(defaultOutboundConfig)
    )

    DaggerApplicationComponent.builder()
      .standardComponent(standard)
      .build()
      .inject(this)

    runBlocking {
      try {
        util.waitForReadySignal()
        util.reportReadyReceived()
      } catch (t: Throwable) {
        util.reportFailure(t)
      }
    }
  }
}
```

In a test environment, rather than executing side-effects against physical process streams,
developers can provide a fake `StandardComponent` populated with test transporters to perform fast,
side-effect-free, in-process stream evaluation. For example:

```kotlin
import com.jackbradshaw.sasync.inbound.config.defaultConfig as defaultInboundConfig
import com.jackbradshaw.sasync.outbound.config.defaultConfig as defaultOutboundConfig
import com.jackbradshaw.sasync.inbound.inboundComponent
import com.jackbradshaw.sasync.outbound.outboundComponent
import com.jackbradshaw.sasync.standard.standardComponent

class UtilTest {
  @Test
  fun executesWithoutSideEffects() = runBlocking {
    val simulatedInput = ByteArrayInputStream("ready\n".toByteArray())
    val simulatedOutput = ByteArrayOutputStream()
    val simulatedError = ByteArrayOutputStream()

    // Create a StandardComponent targeting local memory buffers instead of OS streams
    val testStandardComponent = standardComponent(
        inbound = inboundComponent(defaultInboundConfig),
        outbound = outboundComponent(defaultOutboundConfig),
        input = simulatedInput,
        output = simulatedOutput,
        error = simulatedError
    )

    val util = Util(
        testStandardComponent.standardInputInboundTransport(),
        testStandardComponent.standardOutputOutboundTransport(),
        testStandardComponent.standardErrorOutboundTransport()
    )

    // Passes immediately since simulatedInput has queued data
    util.waitForReadySignal()
    util.reportReadyReceived()

    // Functionally verify output without touching System.out
    assertTrue(simulatedOutput.toString().isEqualTo("READY TO GO"))
  }
}
```

## Performance

Sasync is intended for applications where engineering ergonomics and flow-based programming are
prioritized above absolute real-time performance. While Sasync uses various techniques to improve
performance (e.g. buffering IO) it inherently introduces overhead that cannot be eliminated without
using the streams directly. Consumers are encouraged to consider whether the performance degradation
matters in their application and benchmark accordingly.

## Idle Limitations

Any program which opens a flow from an `InboundTransport` and holds it open indefinitely cannot
reach a natural idle state because the internal polling mechanism is effectively an infinite loop.
This presents a challenge for idle based testing (e.g. testing with systems such as
[Chronosphere](/first_party/chronosphere)). If you need to use Sasync with idle-based testing you
have multiple ways to work around this limitation:

Option 1: Temporary Flows. Design your program so the flow is opened temporarily then closed when no
longer needed (e.g. open it, process a fixed batch via `take(100)`, then close it). Example:

```kotlin
val ui = getUi()
val fileTransport = getFileTransport("foo.txt")

ui.observeUserClickEvent()
  .flatMapLatest {
    fileTransport.observeFlattened().take(100) // Opens the stream temporarily for 100 bytes
  }
  .runningFold(0) { count, _ -> count + 1 }
  .onEach {
    ui.showMessage("Read $it bytes so far")
  }.collect()
```

Option 2: Finite Upstream Sources. Use the transport to read a resource that is known to be finite
so it closes itself and reaches a natural idle. For example:

```kotlin
val fileTransport = getFileTransport("foo.txt")
fileTransport.observeFlattened().onEach {
  println("found byte " + it)
}.collect()
```

Option 3: Substitution via Fake Pulsars. The default implementation of `InboundTransport` uses a
[Pulsar](/first_party/concurrency/pulsar/Pulsar.kt) from the [Concurrency](/first_party/concurrency)
package to drive its loop. By substituting a fake pulsar using Dagger you can take direct control of
the loop which inherently avoids an infinite loop preventing idle. Note: This approach is not
maintainable and is not recommended by the supplier because downstream consumers cannot be expected
to replace arbitrary internals of upstream libraries, nor can they be expected to know the exact
timing details of upstream libraries. Example:

```kotlin
import org.junit.Test
import kotlin.test.assertEquals
import java.io.ByteArrayInputStream
import com.jackbradshaw.sasync.inbound.inboundComponent
import com.jackbradshaw.sasync.inbound.config.defaultConfig
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import com.jackbradshaw.concurrency.testing.testConcurrencyComponent
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList

class TransportTest {
  @Test
  fun testWithFakePulsar() = runBlocking {
    val simulatedInput = ByteArrayInputStream(byteArrayOf(0x01, 0x02))

    // Inject specialized testing variants of the framework's coroutine and concurrency internals.
    val coroutines = realisticCoroutinesTestingComponent()
    val concurrency = testConcurrencyComponent()

    val testTransport = inboundComponent(
        config = defaultConfig,
        coroutines = coroutines,
        concurrency = concurrency
    ).inboundTransportFactory().create(simulatedInput)

    val collected = mutableListOf<Byte>()

    // Launch collection in the background
    CoroutineScope(coroutines.cpuContext()).launch {
      testTransport.observeFlattened().take(2).toList(collected)
    }

    // Ensure the system idles
    coroutines.taskBarrier().awaitAllIdle()

    // No bytes should have been delivered because the test pulsar hasn't been triggered yet.
    assertEquals(0, collected.size)

    // Manually push time forward by firing the test pulsar
    concurrency.testPulsar().emit()
    concurrency.testPulsar().emit()
    coroutines.taskBarrier().awaitAllIdle()

    // 2 bytes should have been delivered now that the test pulsar has been triggered.
    assertEquals(2, collected.size)
  }
}
```

Option 4: Use an Alternative system entirely. For example: Replace Sasync with Java NIO to take
advantage of interrupt-driven systems and avoid the need for polling entirely.

If none of these options are viable, ultimately you have to accept that you have designed a program
that by definition never reaches an idle state because it continues to poll for new data
indefinitely, and thus, is incompatible with idle-based testing.

## Modularity

Interface-based programming is used extensively throughout Sasync such that all tools can be
completely reimplemented by third parties without compromising compatibility with the broader tool
system. For example, you could implement your own `InboundTransport` and it should work with all the
other tools flawlessly. For convenience, abstract tests are provided where sensible, and you can
check your implementations against them. Follow the example in
[InboundTransportTest](/first_party/sasync/inbound/transport/InboundTransportTest.kt).

## Issues

Issues relating to this package and its subpackages are tagged with `sasync`.

## Contributions

Open to contributions from third parties.
