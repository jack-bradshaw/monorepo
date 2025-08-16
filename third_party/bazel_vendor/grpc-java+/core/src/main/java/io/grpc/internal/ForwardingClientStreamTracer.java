/*
 * Copyright 2021 The gRPC Authors
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

import com.google.common.base.MoreObjects;
import io.grpc.Attributes;
import io.grpc.ClientStreamTracer;
import io.grpc.Metadata;
import io.grpc.Status;

public abstract class ForwardingClientStreamTracer extends ClientStreamTracer {

  /**
   * Returns the underlying {@code ClientStreamTracer}.
   */
  protected abstract ClientStreamTracer delegate();

  @Override
  public void streamCreated(Attributes transportAttrs, Metadata headers) {
    delegate().streamCreated(transportAttrs, headers);
  }

  @Override
  public void createPendingStream() {
    delegate().createPendingStream();
  }

  @Override
  public void outboundHeaders() {
    delegate().outboundHeaders();
  }

  @Override
  public void inboundHeaders() {
    delegate().inboundHeaders();
  }

  @Override
  public void inboundHeaders(Metadata headers) {
    delegate().inboundHeaders(headers);
  }

  @Override
  public void inboundTrailers(Metadata trailers) {
    delegate().inboundTrailers(trailers);
  }

  @Override
  public void addOptionalLabel(String key, String value) {
    delegate().addOptionalLabel(key, value);
  }

  @Override
  public void streamClosed(Status status) {
    delegate().streamClosed(status);
  }

  @Override
  public void outboundMessage(int seqNo) {
    delegate().outboundMessage(seqNo);
  }

  @Override
  public void inboundMessage(int seqNo) {
    delegate().inboundMessage(seqNo);
  }

  @Override
  public void outboundMessageSent(int seqNo, long optionalWireSize, long optionalUncompressedSize) {
    delegate().outboundMessageSent(seqNo, optionalWireSize, optionalUncompressedSize);
  }

  @Override
  public void inboundMessageRead(int seqNo, long optionalWireSize, long optionalUncompressedSize) {
    delegate().inboundMessageRead(seqNo, optionalWireSize, optionalUncompressedSize);
  }

  @Override
  public void outboundWireSize(long bytes) {
    delegate().outboundWireSize(bytes);
  }

  @Override
  public void outboundUncompressedSize(long bytes) {
    delegate().outboundUncompressedSize(bytes);
  }

  @Override
  public void inboundWireSize(long bytes) {
    delegate().inboundWireSize(bytes);
  }

  @Override
  public void inboundUncompressedSize(long bytes) {
    delegate().inboundUncompressedSize(bytes);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("delegate", delegate()).toString();
  }
}
