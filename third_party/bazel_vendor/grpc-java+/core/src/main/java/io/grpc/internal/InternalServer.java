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

package io.grpc.internal;

import io.grpc.InternalChannelz.SocketStats;
import io.grpc.InternalInstrumented;
import java.io.IOException;
import java.net.SocketAddress;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

/**
 * An object that accepts new incoming connections on one or more listening socket addresses.
 * This would commonly encapsulate a bound socket that {@code accept()}s new connections.
 */
@ThreadSafe
public interface InternalServer {
  /**
   * Starts transport. Implementations must not call {@code listener} until after {@code start()}
   * returns. The method only returns after it has done the equivalent of bind()ing, so it will be
   * able to service any connections created after returning.
   *
   * @param listener non-{@code null} listener of server events
   * @throws IOException if unable to bind
   */
  void start(ServerListener listener) throws IOException;

  /**
   * Initiates an orderly shutdown of the server. Existing transports continue, but new transports
   * will not be created (once {@link ServerListener#serverShutdown()} callback is called). This
   * method may only be called once.  Blocks until the listening socket(s) have been closed.  If
   * interrupted, this method will not wait for the close to complete, but it will happen
   * asynchronously.
   */
  void shutdown();

  /**
   * Returns the first listening socket address.  May change after {@link #start(ServerListener)} is
   * called.
   */
  SocketAddress getListenSocketAddress();

  /**
   * Returns the first listen socket stats of this server. May return {@code null}.
   */
  @Nullable InternalInstrumented<SocketStats> getListenSocketStats();

  /**
   * Returns a list of listening socket addresses.  May change after {@link #start(ServerListener)}
   * is called.
   */
  List<? extends SocketAddress> getListenSocketAddresses();

  /**
   * Returns a list of listen socket stats of this server. May return {@code null}.
   */
  @Nullable List<InternalInstrumented<SocketStats>> getListenSocketStatsList();

}
