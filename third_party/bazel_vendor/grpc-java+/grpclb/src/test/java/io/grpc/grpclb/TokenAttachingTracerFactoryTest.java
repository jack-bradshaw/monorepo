/*
 * Copyright 2019 The gRPC Authors
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

package io.grpc.grpclb;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.AdditionalAnswers.delegatesTo;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import io.grpc.Attributes;
import io.grpc.ClientStreamTracer;
import io.grpc.Metadata;
import io.grpc.internal.GrpcAttributes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** Unit tests for {@link TokenAttachingTracerFactory}. */
@RunWith(JUnit4.class)
public class TokenAttachingTracerFactoryTest {
  private static final class FakeClientStreamTracer extends ClientStreamTracer {
    Attributes transportAttrs;
    Metadata headers;

    @Override
    public void streamCreated(Attributes transportAttrs, Metadata headers) {
      this.transportAttrs = transportAttrs;
      this.headers = headers;
    }
  }

  private static final FakeClientStreamTracer fakeTracer = new FakeClientStreamTracer();

  private final ClientStreamTracer.Factory delegate = mock(
      ClientStreamTracer.Factory.class,
      delegatesTo(
          new ClientStreamTracer.Factory() {
            @Override
            public ClientStreamTracer newClientStreamTracer(
                ClientStreamTracer.StreamInfo info, Metadata headers) {
              return fakeTracer;
            }
          }));

  @Test
  public void hasToken() {
    TokenAttachingTracerFactory factory = new TokenAttachingTracerFactory(delegate);
    Attributes eagAttrs = Attributes.newBuilder()
        .set(GrpclbConstants.TOKEN_ATTRIBUTE_KEY, "token0001").build();
    ClientStreamTracer.StreamInfo info = ClientStreamTracer.StreamInfo.newBuilder().build();
    Metadata headers = new Metadata();
    // Preexisting token should be replaced
    headers.put(GrpclbConstants.TOKEN_METADATA_KEY, "preexisting-token");

    ClientStreamTracer tracer = factory.newClientStreamTracer(info, headers);
    verify(delegate).newClientStreamTracer(same(info), same(headers));
    Attributes transportAttrs =
        Attributes.newBuilder().set(GrpcAttributes.ATTR_CLIENT_EAG_ATTRS, eagAttrs).build();
    tracer.streamCreated(transportAttrs, headers);
    assertThat(fakeTracer.transportAttrs).isSameInstanceAs(transportAttrs);
    assertThat(fakeTracer.headers).isSameInstanceAs(headers);
    assertThat(headers.getAll(GrpclbConstants.TOKEN_METADATA_KEY)).containsExactly("token0001");
  }

  @Test
  public void noToken() {
    TokenAttachingTracerFactory factory = new TokenAttachingTracerFactory(delegate);
    ClientStreamTracer.StreamInfo info = ClientStreamTracer.StreamInfo.newBuilder().build();

    Metadata headers = new Metadata();
    // Preexisting token should be removed
    headers.put(GrpclbConstants.TOKEN_METADATA_KEY, "preexisting-token");

    ClientStreamTracer tracer = factory.newClientStreamTracer(info, headers);
    verify(delegate).newClientStreamTracer(same(info), same(headers));
    Attributes transportAttrs =
        Attributes.newBuilder().set(GrpcAttributes.ATTR_CLIENT_EAG_ATTRS, Attributes.EMPTY).build();
    tracer.streamCreated(transportAttrs, headers);
    assertThat(fakeTracer.transportAttrs).isSameInstanceAs(transportAttrs);
    assertThat(fakeTracer.headers).isSameInstanceAs(headers);
    assertThat(headers.get(GrpclbConstants.TOKEN_METADATA_KEY)).isNull();
  }

  @Test
  public void nullDelegate() {
    TokenAttachingTracerFactory factory = new TokenAttachingTracerFactory(null);
    ClientStreamTracer.StreamInfo info = ClientStreamTracer.StreamInfo.newBuilder().build();

    Metadata headers = new Metadata();

    ClientStreamTracer tracer = factory.newClientStreamTracer(info, headers);
    tracer.streamCreated(
        Attributes.newBuilder().set(GrpcAttributes.ATTR_CLIENT_EAG_ATTRS, Attributes.EMPTY).build(),
        headers);
    assertThat(tracer).isNotNull();
    assertThat(headers.get(GrpclbConstants.TOKEN_METADATA_KEY)).isNull();
  }
}
