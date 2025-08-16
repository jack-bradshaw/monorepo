/*
 * Copyright 2014 The gRPC Authors
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

package io.grpc;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.concurrent.ThreadSafe;

/**
 * Server for listening for and dispatching incoming calls. It is not expected to be implemented by
 * application code or interceptors.
 */
@ThreadSafe
public abstract class Server {

  /**
   * Key for accessing the {@link Server} instance inside server RPC {@link Context}. It's
   * unclear to us what users would need. If you think you need to use this, please file an
   * issue for us to discuss a public API.
   */
  static final Context.Key<Server> SERVER_CONTEXT_KEY =
      Context.key("io.grpc.Server");

  /**
   * Bind and start the server.  After this call returns, clients may begin connecting to the
   * listening socket(s).
   *
   * @return {@code this} object
   * @throws IllegalStateException if already started or shut down
   * @throws IOException if unable to bind
   * @since 1.0.0
   */
  public abstract Server start() throws IOException;

  /**
   * Returns the port number the server is listening on.  This can return -1 if there is no actual
   * port or the result otherwise does not make sense.  Result is undefined after the server is
   * terminated.  If there are multiple possible ports, this will return one arbitrarily.
   * Implementations are encouraged to return the same port on each call.
   *
   * @see #getListenSockets()
   * @throws IllegalStateException if the server has not yet been started.
   * @since 1.0.0
   */
  public int getPort() {
    return -1;
  }

  /**
   * Returns a list of listening sockets for this server.  May be different than the originally
   * requested sockets (e.g. listening on port '0' may end up listening on a different port).
   * The list is unmodifiable.
   *
   * @throws IllegalStateException if the server has not yet been started.
   * @since 1.19.0
   */
  public List<? extends SocketAddress> getListenSockets() {
    throw new UnsupportedOperationException();
  }

  /**
   * Returns all services registered with the server, or an empty list if not supported by the
   * implementation.
   *
   * @since 1.1.0
   */
  @ExperimentalApi("https://github.com/grpc/grpc-java/issues/2222")
  public List<ServerServiceDefinition> getServices() {
    return Collections.emptyList();
  }

  /**
   * Returns immutable services registered with the server, or an empty list if not supported by the
   * implementation.
   *
   * @since 1.1.0
   */
  @ExperimentalApi("https://github.com/grpc/grpc-java/issues/2222")
  public List<ServerServiceDefinition> getImmutableServices() {
    return Collections.emptyList();
  }


  /**
   * Returns mutable services registered with the server, or an empty list if not supported by the
   * implementation.
   *
   * @since 1.1.0
   */
  @ExperimentalApi("https://github.com/grpc/grpc-java/issues/2222")
  public List<ServerServiceDefinition> getMutableServices() {
    return Collections.emptyList();
  }

  /**
   * Initiates an orderly shutdown in which preexisting calls continue but new calls are rejected.
   * After this call returns, this server has released the listening socket(s) and may be reused by
   * another server.
   *
   * <p>Note that this method will not wait for preexisting calls to finish before returning.
   * {@link #awaitTermination()} or {@link #awaitTermination(long, TimeUnit)} needs to be called to
   * wait for existing calls to finish.
   *
   * <p>Calling this method before {@code start()} will shut down and terminate the server like
   * normal, but prevents starting the server in the future.
   *
   * @return {@code this} object
   * @since 1.0.0
   */
  public abstract Server shutdown();

  /**
   * Initiates a forceful shutdown in which preexisting and new calls are rejected. Although
   * forceful, the shutdown process is still not instantaneous; {@link #isTerminated()} will likely
   * return {@code false} immediately after this method returns. After this call returns, this
   * server has released the listening socket(s) and may be reused by another server.
   *
   * <p>Calling this method before {@code start()} will shut down and terminate the server like
   * normal, but prevents starting the server in the future.
   *
   * @return {@code this} object
   * @since 1.0.0
   */
  public abstract Server shutdownNow();

  /**
   * Returns whether the server is shutdown. Shutdown servers reject any new calls, but may still
   * have some calls being processed.
   *
   * @see #shutdown()
   * @see #isTerminated()
   * @since 1.0.0
   */
  public abstract boolean isShutdown();

  /**
   * Returns whether the server is terminated. Terminated servers have no running calls and
   * relevant resources released (like TCP connections).
   *
   * @see #isShutdown()
   * @since 1.0.0
   */
  public abstract boolean isTerminated();

  /**
   * Waits for the server to become terminated, giving up if the timeout is reached.
   *
   * <p>Calling this method before {@code start()} or {@code shutdown()} is permitted and does not
   * change its behavior.
   *
   * @return whether the server is terminated, as would be done by {@link #isTerminated()}.
   */
  public abstract boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException;

  /**
   * Waits for the server to become terminated.
   *
   * <p>Calling this method before {@code start()} or {@code shutdown()} is permitted and does not
   * change its behavior.
   *
   * @since 1.0.0
   */
  public abstract void awaitTermination() throws InterruptedException;
}
