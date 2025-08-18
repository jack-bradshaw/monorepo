/*
 * Copyright 2016 The gRPC Authors
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

package io.grpc.netty;

import com.google.common.annotations.VisibleForTesting;
import io.grpc.Internal;
import io.grpc.internal.ClientTransportFactory;
import io.grpc.internal.GrpcUtil;
import io.grpc.internal.SharedResourcePool;
import io.grpc.internal.TransportTracer;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.InetSocketAddress;

/**
 * Internal {@link NettyChannelBuilder} accessor.  This is intended for usage internal to the gRPC
 * team.  If you *really* think you need to use this, contact the gRPC team first.
 */
@Internal
public final class InternalNettyChannelBuilder {

  public static void disableCheckAuthority(NettyChannelBuilder builder) {
    builder.disableCheckAuthority();
  }

  public static void enableCheckAuthority(NettyChannelBuilder builder) {
    builder.enableCheckAuthority();
  }

  /** A class that provides a Netty handler to control protocol negotiation. */
  public interface ProtocolNegotiatorFactory {
    InternalProtocolNegotiator.ProtocolNegotiator buildProtocolNegotiator();
  }

  /**
   * Sets the {@link ProtocolNegotiatorFactory} to be used. Overrides any specified negotiation type
   * and {@code SslContext}.
   */
  public static void setProtocolNegotiatorFactory(
      NettyChannelBuilder builder, final ProtocolNegotiatorFactory protocolNegotiator) {
    builder.protocolNegotiatorFactory(new ProtocolNegotiator.ClientFactory() {
      @Override public ProtocolNegotiator newNegotiator() {
        return protocolNegotiator.buildProtocolNegotiator();
      }

      @Override public int getDefaultPort() {
        return GrpcUtil.DEFAULT_PORT_SSL;
      }
    });
  }

  /**
   * Sets the {@link ProtocolNegotiatorFactory} to be used. Overrides any specified negotiation type
   * and {@code SslContext}.
   */
  public static void setProtocolNegotiatorFactory(
      NettyChannelBuilder builder, InternalProtocolNegotiator.ClientFactory protocolNegotiator) {
    builder.protocolNegotiatorFactory(protocolNegotiator);
  }

  public static void setStatsEnabled(NettyChannelBuilder builder, boolean value) {
    builder.setStatsEnabled(value);
  }

  public static void setTracingEnabled(NettyChannelBuilder builder, boolean value) {
    builder.setTracingEnabled(value);
  }

  public static void setStatsRecordStartedRpcs(NettyChannelBuilder builder, boolean value) {
    builder.setStatsRecordStartedRpcs(value);
  }

  public static void setStatsRecordFinishedRpcs(NettyChannelBuilder builder, boolean value) {
    builder.setStatsRecordFinishedRpcs(value);
  }

  public static void setStatsRecordRealTimeMetrics(NettyChannelBuilder builder, boolean value) {
    builder.setStatsRecordRealTimeMetrics(value);
  }

  public static void setStatsRecordRetryMetrics(NettyChannelBuilder builder, boolean value) {
    builder.setStatsRecordRetryMetrics(value);
  }

  /**
   * Sets {@link io.grpc.Channel} and {@link io.netty.channel.EventLoopGroup} to Nio. A major
   * benefit over using setters is gRPC will manage the life cycle of {@link
   * io.netty.channel.EventLoopGroup}.
   */
  public static void useNioTransport(NettyChannelBuilder builder) {
    builder.channelType(NioSocketChannel.class, InetSocketAddress.class);
    builder
        .eventLoopGroupPool(SharedResourcePool.forResource(Utils.NIO_WORKER_EVENT_LOOP_GROUP));
  }

  public static ClientTransportFactory buildTransportFactory(NettyChannelBuilder builder) {
    return builder.buildTransportFactory();
  }

  @VisibleForTesting
  public static void setTransportTracerFactory(
      NettyChannelBuilder builder, TransportTracer.Factory factory) {
    builder.setTransportTracerFactory(factory);
  }

  private InternalNettyChannelBuilder() {}
}
