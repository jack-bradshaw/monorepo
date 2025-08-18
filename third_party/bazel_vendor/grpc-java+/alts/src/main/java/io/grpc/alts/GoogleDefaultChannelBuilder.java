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

import io.grpc.ForwardingChannelBuilder;
import io.grpc.ManagedChannelBuilder;
import io.grpc.internal.GrpcUtil;
import io.grpc.netty.NettyChannelBuilder;

/**
 * Google default version of {@code ManagedChannelBuilder}. This class sets up a secure channel
 * using ALTS if applicable and using TLS as fallback.
 */
public final class GoogleDefaultChannelBuilder
    extends ForwardingChannelBuilder<GoogleDefaultChannelBuilder> {

  private final NettyChannelBuilder delegate;

  private GoogleDefaultChannelBuilder(String target) {
    delegate = NettyChannelBuilder.forTarget(target, GoogleDefaultChannelCredentials.create());
  }

  /** "Overrides" the static method in {@link ManagedChannelBuilder}. */
  public static GoogleDefaultChannelBuilder forTarget(String target) {
    return new GoogleDefaultChannelBuilder(target);
  }

  /** "Overrides" the static method in {@link ManagedChannelBuilder}. */
  public static GoogleDefaultChannelBuilder forAddress(String name, int port) {
    return forTarget(GrpcUtil.authorityFromHostAndPort(name, port));
  }

  @Override
  @SuppressWarnings("deprecation") // Not extending ForwardingChannelBuilder2 to preserve ABI.
  protected NettyChannelBuilder delegate() {
    return delegate;
  }
}
