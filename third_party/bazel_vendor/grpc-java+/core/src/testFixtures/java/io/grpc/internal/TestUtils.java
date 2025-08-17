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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.grpc.Attributes;
import io.grpc.CallOptions;
import io.grpc.ChannelLogger;
import io.grpc.ClientStreamTracer;
import io.grpc.EquivalentAddressGroup;
import io.grpc.InternalLogId;
import io.grpc.LoadBalancer.PickResult;
import io.grpc.LoadBalancer.PickSubchannelArgs;
import io.grpc.LoadBalancer.Subchannel;
import io.grpc.LoadBalancer.SubchannelPicker;
import io.grpc.LoadBalancerProvider;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import java.net.SocketAddress;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.annotation.Nullable;
import org.mockito.ArgumentMatchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * Common utility methods for tests.
 */
public final class TestUtils {

  /** Base class for a standard LoadBalancerProvider implementation. */
  public abstract static class StandardLoadBalancerProvider extends LoadBalancerProvider {
    private final String policyName;

    protected StandardLoadBalancerProvider(String policyName) {
      this.policyName = policyName;
    }

    @Override
    public boolean isAvailable() {
      return true;
    }

    @Override
    public int getPriority() {
      return 5;
    }

    @Override
    public final String getPolicyName() {
      return policyName;
    }
  }

  /** Creates a {@link SubchannelPicker} that returns the given {@link Subchannel} on every pick. */
  public static SubchannelPicker pickerOf(final Subchannel subchannel) {
    return new SubchannelPicker() {
      @Override
      public PickResult pickSubchannel(PickSubchannelArgs args) {
        return PickResult.withSubchannel(subchannel);
      }
    };
  }

  static class MockClientTransportInfo {
    /**
     * A mock transport created by the mock transport factory.
     */
    final ConnectionClientTransport transport;

    /**
     * The listener passed to the start() of the mock transport.
     */
    final ManagedClientTransport.Listener listener;

    MockClientTransportInfo(ConnectionClientTransport transport,
        ManagedClientTransport.Listener listener) {
      this.transport = transport;
      this.listener = listener;
    }
  }

  /**
   * Stub the given mock {@link ClientTransportFactory} by returning mock
   * {@link ManagedClientTransport}s which saves their listeners along with them. This method
   * returns a list of {@link MockClientTransportInfo}, each of which is a started mock transport
   * and its listener.
   */
  static BlockingQueue<MockClientTransportInfo> captureTransports(
      ClientTransportFactory mockTransportFactory) {
    return captureTransports(mockTransportFactory, null);
  }

  static BlockingQueue<MockClientTransportInfo> captureTransports(
      ClientTransportFactory mockTransportFactory, @Nullable final Runnable startRunnable) {
    final BlockingQueue<MockClientTransportInfo> captor =
        new LinkedBlockingQueue<>();

    doAnswer(new Answer<ConnectionClientTransport>() {
      @Override
      public ConnectionClientTransport answer(InvocationOnMock invocation) throws Throwable {
        final ConnectionClientTransport mockTransport = mock(ConnectionClientTransport.class);
        when(mockTransport.getLogId())
            .thenReturn(InternalLogId.allocate("mocktransport", /*details=*/ null));
        when(mockTransport.newStream(
                any(MethodDescriptor.class), any(Metadata.class), any(CallOptions.class),
                ArgumentMatchers.<ClientStreamTracer[]>any()))
            .thenReturn(mock(ClientStream.class));
        // Save the listener
        doAnswer(new Answer<Runnable>() {
          @Override
          public Runnable answer(InvocationOnMock invocation) throws Throwable {
            captor.add(new MockClientTransportInfo(
                mockTransport, (ManagedClientTransport.Listener) invocation.getArguments()[0]));
            return startRunnable;
          }
        }).when(mockTransport).start(any(ManagedClientTransport.Listener.class));
        return mockTransport;
      }
    }).when(mockTransportFactory)
        .newClientTransport(
            any(SocketAddress.class),
            any(ClientTransportFactory.ClientTransportOptions.class),
            any(ChannelLogger.class));

    return captor;
  }

  @SuppressWarnings("ReferenceEquality")
  public static EquivalentAddressGroup stripAttrs(EquivalentAddressGroup eag) {
    if (eag.getAttributes() == Attributes.EMPTY) {
      return eag;
    }
    return new EquivalentAddressGroup(eag.getAddresses());
  }

  private TestUtils() {
  }

  public static class NoopChannelLogger extends ChannelLogger {

    @Override
    public void log(ChannelLogLevel level, String message) {}

    @Override
    public void log(ChannelLogLevel level, String messageFormat, Object... args) {}
  }
}
