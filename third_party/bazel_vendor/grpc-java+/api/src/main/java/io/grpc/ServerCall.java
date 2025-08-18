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

import static com.google.common.base.Preconditions.checkArgument;

import javax.annotation.Nullable;

/**
 * Encapsulates a single call received from a remote client. Calls may not simply be unary
 * request-response even though this is the most common pattern. Calls may stream any number of
 * requests and responses. This API is generally intended for use by generated handlers,
 * but applications may use it directly if they need to.
 *
 * <p>Headers must be sent before any messages, which must be sent before closing.
 *
 * <p>No generic method for determining message receipt or providing acknowledgement is provided.
 * Applications are expected to utilize normal messages for such signals, as a response
 * naturally acknowledges its request.
 *
 * <p>Methods are guaranteed to be non-blocking. Implementations are not required to be thread-safe.
 *
 * <p>DO NOT MOCK: Use InProcessTransport and make a fake server instead.
 *
 * @param <ReqT> parsed type of request message.
 * @param <RespT> parsed type of response message.
 */
public abstract class ServerCall<ReqT, RespT> {

  /**
   * Callbacks for consuming incoming RPC messages.
   *
   * <p>Any contexts are guaranteed to arrive before any messages, which are guaranteed before half
   * close, which is guaranteed before completion.
   *
   * <p>Implementations are free to block for extended periods of time. Implementations are not
   * required to be thread-safe, but they must not be thread-hostile. The caller is free to call
   * an instance from multiple threads, but only one call simultaneously. A single thread may
   * interleave calls to multiple instances, so implementations using ThreadLocals must be careful
   * to avoid leaking inappropriate state (e.g., clearing the ThreadLocal before returning).
   */
  // TODO(ejona86): We need to decide what to do in the case of server closing with non-cancellation
  // before client half closes. It may be that we treat such a case as an error. If we permit such
  // a case then we either get to generate a half close or purposefully omit it.
  public abstract static class Listener<ReqT> {
    /**
     * A request message has been received. For streaming calls, there may be zero or more request
     * messages.
     *
     * @param message a received request message.
     */
    public void onMessage(ReqT message) {}

    /**
     * The client completed all message sending. However, the call may still be cancelled.
     */
    public void onHalfClose() {}

    /**
     * The call was cancelled and the server is encouraged to abort processing to save resources,
     * since the client will not process any further messages. Cancellations can be caused by
     * timeouts, explicit cancellation by the client, network errors, etc.
     *
     * <p>There will be no further callbacks for the call.
     */
    public void onCancel() {}

    /**
     * The call is considered complete and {@link #onCancel} is guaranteed not to be called.
     * However, the client is not guaranteed to have received all messages.
     *
     * <p>There will be no further callbacks for the call.
     */
    public void onComplete() {}

    /**
     * This indicates that the call may now be capable of sending additional messages (via
     * {@link #sendMessage}) without requiring excessive buffering internally. This event is
     * just a suggestion and the application is free to ignore it, however doing so may
     * result in excessive buffering within the call.
     *
     * <p>Because there is a processing delay to deliver this notification, it is possible for
     * concurrent writes to cause {@code isReady() == false} within this callback. Handle "spurious"
     * notifications by checking {@code isReady()}'s current value instead of assuming it is now
     * {@code true}. If {@code isReady() == false} the normal expectations apply, so there would be
     * <em>another</em> {@code onReady()} callback.
     */
    public void onReady() {}
  }

  /**
   * Requests up to the given number of messages from the call to be delivered to
   * {@link Listener#onMessage(Object)}. Once {@code numMessages} have been delivered
   * no further request messages will be delivered until more messages are requested by
   * calling this method again.
   *
   * <p>Servers use this mechanism to provide back-pressure to the client for flow-control.
   *
   * <p>This method is safe to call from multiple threads without external synchronization.
   *
   * @param numMessages the requested number of messages to be delivered to the listener.
   */
  public abstract void request(int numMessages);

  /**
   * Send response header metadata prior to sending a response message. This method may
   * only be called once and cannot be called after calls to {@link #sendMessage} or {@link #close}.
   *
   * <p>Since {@link Metadata} is not thread-safe, the caller must not access (read or write) {@code
   * headers} after this point.
   *
   * @param headers metadata to send prior to any response body.
   * @throws IllegalStateException if {@code close} has been called, a message has been sent, or
   *     headers have already been sent
   */
  public abstract void sendHeaders(Metadata headers);

  /**
   * Send a response message. Messages are the primary form of communication associated with
   * RPCs. Multiple response messages may exist for streaming calls.
   *
   * @param message response message.
   * @throws IllegalStateException if headers not sent or call is {@link #close}d
   */
  public abstract void sendMessage(RespT message);

  /**
   * If {@code true}, indicates that the call is capable of sending additional messages
   * without requiring excessive buffering internally. This event is
   * just a suggestion and the application is free to ignore it, however doing so may
   * result in excessive buffering within the call.
   *
   * <p>If {@code false}, {@link Listener#onReady()} will be called after {@code isReady()}
   * transitions to {@code true}.
   *
   * <p>This abstract class's implementation always returns {@code true}. Implementations generally
   * override the method.
   */
  public boolean isReady() {
    return true;
  }

  /**
   * Close the call with the provided status. No further sending or receiving will occur. If {@link
   * Status#isOk} is {@code false}, then the call is said to have failed.
   *
   * <p>If no errors or cancellations are known to have occurred, then a {@link Listener#onComplete}
   * notification should be expected, independent of {@code status}. Otherwise {@link
   * Listener#onCancel} has been or will be called.
   *
   * <p>Since {@link Metadata} is not thread-safe, the caller must not access (read or write) {@code
   * trailers} after this point.
   *
   * <p>This method implies the caller completed processing the RPC, but it does not imply the RPC
   * is complete. The call implementation will need additional time to complete the RPC and during
   * this time the client is still able to cancel the request or a network error might cause the
   * RPC to fail. If you wish to know when the call is actually completed/closed, you have to use
   * {@link Listener#onComplete} or {@link Listener#onCancel} instead. This method is not
   * necessarily invoked when Listener.onCancel() is called.
   *
   * @throws IllegalStateException if call is already {@code close}d
   */
  public abstract void close(Status status, Metadata trailers);

  /**
   * Returns {@code true} when the call is cancelled and the server is encouraged to abort
   * processing to save resources, since the client will not be processing any further methods.
   * Cancellations can be caused by timeouts, explicit cancel by client, network errors, and
   * similar.
   *
   * <p>This method may safely be called concurrently from multiple threads.
   */
  public abstract boolean isCancelled();

  /**
   * Enables per-message compression, if an encoding type has been negotiated.  If no message
   * encoding has been negotiated, this is a no-op. By default per-message compression is enabled,
   * but may not have any effect if compression is not enabled on the call.
   */
  public void setMessageCompression(boolean enabled) {
    // noop
  }

  /**
   * Sets the compression algorithm for this call.  This compression is utilized for sending.  If
   * the server does not support the compression algorithm, the call will fail.  This method may
   * only be called before {@link #sendHeaders}.  The compressor to use will be looked up in the
   * {@link CompressorRegistry}.  Default gRPC servers support the "gzip" compressor.
   *
   * <p>It is safe to call this even if the client does not support the compression format chosen.
   * The implementation will handle negotiation with the client and may fall back to no compression.
   *
   * @param compressor the name of the compressor to use.
   * @throws IllegalArgumentException if the compressor name can not be found.
   */
  public void setCompression(String compressor) {
    // noop
  }

  /**
   * A hint to the call that specifies how many bytes must be queued before
   * {@link #isReady()} will return false. A call may ignore this property if
   * unsupported. This may only be set before any messages are sent.
   *
   * @param numBytes The number of bytes that must be queued. Must be a
   *                 positive integer.
   */
  @ExperimentalApi("https://github.com/grpc/grpc-java/issues/11021")
  public void setOnReadyThreshold(int numBytes) {
    checkArgument(numBytes > 0, "numBytes must be positive: %s", numBytes);
  }

  /**
   * Returns the level of security guarantee in communications
   *
   * <p>Determining the level of security offered by the transport for RPCs on server-side.
   * This can be approximated by looking for the SSLSession, but that doesn't work for ALTS and
   * maybe some future TLS approaches. May return a lower security level when it cannot be
   * determined precisely.
   *
   * @return non-{@code null} SecurityLevel enum
   */
  @ExperimentalApi("https://github.com/grpc/grpc-java/issues/4692")
  public SecurityLevel getSecurityLevel() {
    return SecurityLevel.NONE;
  }

  /**
   * Returns properties of a single call.
   *
   * <p>Attributes originate from the transport and can be altered by {@link ServerTransportFilter}.
   *
   * @return non-{@code null} Attributes container
   */
  @ExperimentalApi("https://github.com/grpc/grpc-java/issues/1779")
  @Grpc.TransportAttr
  public Attributes getAttributes() {
    return Attributes.EMPTY;
  }

  /**
   * Gets the authority this call is addressed to.
   *
   * @return the authority string. {@code null} if not available.
   */
  @Nullable
  public String getAuthority() {
    return null;
  }

  /**
   * The {@link MethodDescriptor} for the call.
   */
  public abstract MethodDescriptor<ReqT, RespT> getMethodDescriptor();
}
