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

package io.grpc.netty;

import io.grpc.ChannelCredentials;
import io.grpc.Internal;
import io.grpc.ManagedChannelProvider;
import java.net.SocketAddress;
import java.util.Collection;

/** Provider for {@link NettyChannelBuilder} instances. */
@Internal
public final class NettyChannelProvider extends ManagedChannelProvider {
  @Override
  public boolean isAvailable() {
    return true;
  }

  @Override
  public int priority() {
    return 5;
  }

  @Override
  public NettyChannelBuilder builderForAddress(String name, int port) {
    return NettyChannelBuilder.forAddress(name, port);
  }

  @Override
  public NettyChannelBuilder builderForTarget(String target) {
    return NettyChannelBuilder.forTarget(target);
  }

  @Override
  public NewChannelBuilderResult newChannelBuilder(String target, ChannelCredentials creds) {
    ProtocolNegotiators.FromChannelCredentialsResult result = ProtocolNegotiators.from(creds);
    if (result.error != null) {
      return NewChannelBuilderResult.error(result.error);
    }
    return NewChannelBuilderResult.channelBuilder(
        new NettyChannelBuilder(target, creds, result.callCredentials, result.negotiator));
  }

  @Override
  protected Collection<Class<? extends SocketAddress>> getSupportedSocketAddressTypes() {
    return NettyChannelBuilder.getSupportedSocketAddressTypes();
  }
}
