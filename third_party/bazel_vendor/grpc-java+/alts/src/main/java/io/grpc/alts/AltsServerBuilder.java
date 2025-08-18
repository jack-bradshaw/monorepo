/*
 * Copyright 2018 The gRPC Authors
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

package io.grpc.alts;

import io.grpc.BindableService;
import io.grpc.CompressorRegistry;
import io.grpc.DecompressorRegistry;
import io.grpc.ExperimentalApi;
import io.grpc.ForwardingServerBuilder;
import io.grpc.HandlerRegistry;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptor;
import io.grpc.ServerServiceDefinition;
import io.grpc.ServerStreamTracer;
import io.grpc.ServerTransportFilter;
import io.grpc.netty.NettyServerBuilder;
import java.io.File;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * gRPC secure server builder used for ALTS. This class adds on the necessary ALTS support to create
 * a production server on Google Cloud Platform.
 */
@ExperimentalApi("https://github.com/grpc/grpc-java/issues/4151")
public final class AltsServerBuilder extends ForwardingServerBuilder<AltsServerBuilder> {
  private final NettyServerBuilder delegate;
  private final AltsServerCredentials.Builder credentialsBuilder =
      new AltsServerCredentials.Builder();

  private AltsServerBuilder(NettyServerBuilder nettyDelegate) {
    this.delegate = nettyDelegate;
  }

  /** Creates a gRPC server builder for the given port. */
  public static AltsServerBuilder forPort(int port) {
    NettyServerBuilder nettyDelegate = NettyServerBuilder.forAddress(new InetSocketAddress(port));
    return new AltsServerBuilder(nettyDelegate);
  }

  /**
   * Enables untrusted ALTS for testing. If this function is called, we will not check whether ALTS
   * is running on Google Cloud Platform.
   */
  public AltsServerBuilder enableUntrustedAltsForTesting() {
    credentialsBuilder.enableUntrustedAltsForTesting();
    return this;
  }

  /** Sets a new handshaker service address for testing. */
  public AltsServerBuilder setHandshakerAddressForTesting(String handshakerAddress) {
    credentialsBuilder.setHandshakerAddressForTesting(handshakerAddress);
    return this;
  }

  @Override
  protected ServerBuilder<?> delegate() {
    return delegate;
  }

  /** {@inheritDoc} */
  @Override
  public AltsServerBuilder handshakeTimeout(long timeout, TimeUnit unit) {
    delegate.handshakeTimeout(timeout, unit);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public AltsServerBuilder directExecutor() {
    delegate.directExecutor();
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public AltsServerBuilder addStreamTracerFactory(ServerStreamTracer.Factory factory) {
    delegate.addStreamTracerFactory(factory);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public AltsServerBuilder addTransportFilter(ServerTransportFilter filter) {
    delegate.addTransportFilter(filter);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public AltsServerBuilder executor(Executor executor) {
    delegate.executor(executor);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public AltsServerBuilder addService(ServerServiceDefinition service) {
    delegate.addService(service);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public AltsServerBuilder addService(BindableService bindableService) {
    delegate.addService(bindableService);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public AltsServerBuilder fallbackHandlerRegistry(HandlerRegistry fallbackRegistry) {
    delegate.fallbackHandlerRegistry(fallbackRegistry);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public AltsServerBuilder useTransportSecurity(File certChain, File privateKey) {
    throw new UnsupportedOperationException("Can't set TLS settings for ALTS");
  }

  /** {@inheritDoc} */
  @Override
  public AltsServerBuilder decompressorRegistry(DecompressorRegistry registry) {
    delegate.decompressorRegistry(registry);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public AltsServerBuilder compressorRegistry(CompressorRegistry registry) {
    delegate.compressorRegistry(registry);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public AltsServerBuilder intercept(ServerInterceptor interceptor) {
    delegate.intercept(interceptor);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public Server build() {
    delegate.protocolNegotiator(credentialsBuilder.buildProtocolNegotiator());
    return delegate.build();
  }
}
