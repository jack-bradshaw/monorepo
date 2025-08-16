/*
 * Copyright 2015 The gRPC Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.grpc.inprocess;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.DoNotCall;
import io.grpc.Deadline;
import io.grpc.ExperimentalApi;
import io.grpc.ForwardingServerBuilder;
import io.grpc.Internal;
import io.grpc.ServerBuilder;
import io.grpc.ServerStreamTracer;
import io.grpc.internal.FixedObjectPool;
import io.grpc.internal.GrpcUtil;
import io.grpc.internal.InternalServer;
import io.grpc.internal.ObjectPool;
import io.grpc.internal.ServerImplBuilder;
import io.grpc.internal.ServerImplBuilder.ClientTransportServersBuilder;
import io.grpc.internal.SharedResourcePool;
import java.io.File;
import java.net.SocketAddress;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Builder for a server that services in-process requests. Clients identify the in-process server by
 * its name.
 *
 * <p>The server is intended to be fully-featured, high performance, and useful in testing.
 *
 * <h3>Using JUnit TestRule</h3>
 * The class "GrpcServerRule" (from "grpc-java/testing") is a JUnit TestRule that
 * creates a {@link InProcessServer} and a {@link io.grpc.ManagedChannel ManagedChannel}. This
 * test rule contains the boilerplate code shown below. The classes "HelloWorldServerTest" and
 * "HelloWorldClientTest" (from "grpc-java/examples") demonstrate basic usage.
 *
 * <h3>Usage example</h3>
 * <h4>Server and client channel setup</h4>
 * <pre>
 *   String uniqueName = InProcessServerBuilder.generateName();
 *   Server server = InProcessServerBuilder.forName(uniqueName)
 *       .directExecutor() // directExecutor is fine for unit tests
 *       .addService(&#47;* your code here *&#47;)
 *       .build().start();
 *   ManagedChannel channel = InProcessChannelBuilder.forName(uniqueName)
 *       .directExecutor()
 *       .build();
 * </pre>
 *
 * <h4>Usage in tests</h4>
 * The channel can be used normally. A blocking stub example:
 * <pre>
 *   TestServiceGrpc.TestServiceBlockingStub blockingStub =
 *       TestServiceGrpc.newBlockingStub(channel);
 * </pre>
 */
@ExperimentalApi("https://github.com/grpc/grpc-java/issues/1783")
public final class InProcessServerBuilder extends ForwardingServerBuilder<InProcessServerBuilder> {
  /**
   * Create a server builder that will bind with the given name.
   *
   * @param name the identity of the server for clients to connect to
   * @return a new builder
   */
  public static InProcessServerBuilder forName(String name) {
    return forAddress(new InProcessSocketAddress(checkNotNull(name, "name")));
  }

  /**
   * Create a server builder which listens on the given address.
   * @param listenAddress The SocketAddress this server will listen on.
   * @return a new builder
   */
  public static InProcessServerBuilder forAddress(SocketAddress listenAddress) {
    return new InProcessServerBuilder(listenAddress);
  }

  /**
   * Always fails.  Call {@link #forName} instead.
   */
  @DoNotCall("Unsupported. Use forName() instead")
  public static InProcessServerBuilder forPort(int port) {
    throw new UnsupportedOperationException("call forName() instead");
  }

  /**
   * Generates a new server name that is unique each time.
   */
  public static String generateName() {
    return UUID.randomUUID().toString();
  }

  private final ServerImplBuilder serverImplBuilder;
  final SocketAddress listenAddress;
  int maxInboundMetadataSize = Integer.MAX_VALUE;
  ObjectPool<ScheduledExecutorService> schedulerPool =
      SharedResourcePool.forResource(GrpcUtil.TIMER_SERVICE);

  private InProcessServerBuilder(SocketAddress listenAddress) {
    this.listenAddress = checkNotNull(listenAddress, "listenAddress");

    final class InProcessClientTransportServersBuilder implements ClientTransportServersBuilder {
      @Override
      public InternalServer buildClientTransportServers(
          List<? extends ServerStreamTracer.Factory> streamTracerFactories) {
        return buildTransportServers(streamTracerFactories);
      }
    }

    serverImplBuilder = new ServerImplBuilder(new InProcessClientTransportServersBuilder());

    // In-process transport should not record its traffic to the stats module.
    // https://github.com/grpc/grpc-java/issues/2284
    serverImplBuilder.setStatsRecordStartedRpcs(false);
    serverImplBuilder.setStatsRecordFinishedRpcs(false);
    // Disable handshake timeout because it is unnecessary, and can trigger Thread creation that can
    // break some environments (like tests).
    handshakeTimeout(Long.MAX_VALUE, TimeUnit.SECONDS);
  }

  @Internal
  @Override
  protected ServerBuilder<?> delegate() {
    return serverImplBuilder;
  }

  /**
   * Provides a custom scheduled executor service.
   *
   * <p>It's an optional parameter. If the user has not provided a scheduled executor service when
   * the channel is built, the builder will use a static cached thread pool.
   *
   * @return this
   *
   * @since 1.11.0
   */
  public InProcessServerBuilder scheduledExecutorService(
      ScheduledExecutorService scheduledExecutorService) {
    schedulerPool = new FixedObjectPool<>(
        checkNotNull(scheduledExecutorService, "scheduledExecutorService"));
    return this;
  }

  /**
   * Provides a custom deadline ticker that this server will use to create incoming {@link
   * Deadline}s.
   *
   * <p>This is intended for unit tests that fake out the clock.  You should also have a fake {@link
   * ScheduledExecutorService} whose clock is synchronized with this ticker and set it to {@link
   * #scheduledExecutorService}. DO NOT use this in production.
   *
   * @return this
   * @see Deadline#after(long, TimeUnit, Deadline.Ticker)
   *
   * @since 1.24.0
   */
  public InProcessServerBuilder deadlineTicker(Deadline.Ticker ticker) {
    serverImplBuilder.setDeadlineTicker(ticker);
    return this;
  }

  /**
   * Sets the maximum size of metadata allowed to be received. {@code Integer.MAX_VALUE} disables
   * the enforcement. Defaults to no limit ({@code Integer.MAX_VALUE}).
   *
   * <p>There is potential for performance penalty when this setting is enabled, as the Metadata
   * must actually be serialized. Since the current implementation of Metadata pre-serializes, it's
   * currently negligible. But Metadata is free to change its implementation.
   *
   * @param bytes the maximum size of received metadata
   * @return this
   * @throws IllegalArgumentException if bytes is non-positive
   * @since 1.17.0
   */
  @Override
  public InProcessServerBuilder maxInboundMetadataSize(int bytes) {
    Preconditions.checkArgument(bytes > 0, "maxInboundMetadataSize must be > 0");
    this.maxInboundMetadataSize = bytes;
    return this;
  }

  InProcessServer buildTransportServers(
      List<? extends ServerStreamTracer.Factory> streamTracerFactories) {
    return new InProcessServer(this, streamTracerFactories);
  }

  @Override
  public InProcessServerBuilder useTransportSecurity(File certChain, File privateKey) {
    throw new UnsupportedOperationException("TLS not supported in InProcessServer");
  }

  void setStatsEnabled(boolean value) {
    this.serverImplBuilder.setStatsEnabled(value);
  }
}
