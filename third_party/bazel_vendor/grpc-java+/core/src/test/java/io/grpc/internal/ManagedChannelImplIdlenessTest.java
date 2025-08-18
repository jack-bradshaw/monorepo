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

package io.grpc.internal;

import static com.google.common.truth.Truth.assertThat;
import static io.grpc.ConnectivityState.READY;
import static io.grpc.ConnectivityState.TRANSIENT_FAILURE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.AdditionalAnswers.delegatesTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.common.collect.Lists;
import io.grpc.Attributes;
import io.grpc.CallOptions;
import io.grpc.ChannelLogger;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ConnectivityState;
import io.grpc.EquivalentAddressGroup;
import io.grpc.IntegerMarshaller;
import io.grpc.LoadBalancer;
import io.grpc.LoadBalancer.CreateSubchannelArgs;
import io.grpc.LoadBalancer.Helper;
import io.grpc.LoadBalancer.PickResult;
import io.grpc.LoadBalancer.PickSubchannelArgs;
import io.grpc.LoadBalancer.ResolvedAddresses;
import io.grpc.LoadBalancer.Subchannel;
import io.grpc.LoadBalancer.SubchannelPicker;
import io.grpc.LoadBalancer.SubchannelStateListener;
import io.grpc.LoadBalancerProvider;
import io.grpc.LoadBalancerRegistry;
import io.grpc.ManagedChannel;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.MethodDescriptor.MethodType;
import io.grpc.NameResolver;
import io.grpc.NameResolver.ResolutionResult;
import io.grpc.NameResolverProvider;
import io.grpc.Status;
import io.grpc.StatusOr;
import io.grpc.StringMarshaller;
import io.grpc.internal.FakeClock.ScheduledTask;
import io.grpc.internal.ManagedChannelImplBuilder.UnsupportedClientTransportFactoryBuilder;
import io.grpc.internal.TestUtils.MockClientTransportInfo;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

/**
 * Unit tests for {@link ManagedChannelImpl}'s idle mode.
 */
@RunWith(JUnit4.class)
public class ManagedChannelImplIdlenessTest {
  @Rule
  public final MockitoRule mocks = MockitoJUnit.rule();
  private final FakeClock timer = new FakeClock();
  private final FakeClock executor = new FakeClock();
  private final FakeClock oobExecutor = new FakeClock();
  private static final String AUTHORITY = "fakeauthority";
  private static final String USER_AGENT = "fakeagent";
  private static final long IDLE_TIMEOUT_SECONDS = 30;
  private static final String MOCK_POLICY_NAME = "mock_lb";
  private ManagedChannelImpl channel;

  private final MethodDescriptor<String, Integer> method =
      MethodDescriptor.<String, Integer>newBuilder()
          .setType(MethodType.UNKNOWN)
          .setFullMethodName("service/method")
          .setRequestMarshaller(new StringMarshaller())
          .setResponseMarshaller(new IntegerMarshaller())
          .build();

  private final List<EquivalentAddressGroup> servers = Lists.newArrayList();
  private final ObjectPool<Executor> executorPool =
      new FixedObjectPool<Executor>(executor.getScheduledExecutorService());
  private final ObjectPool<Executor> oobExecutorPool =
      new FixedObjectPool<Executor>(oobExecutor.getScheduledExecutorService());

  @Mock private ClientTransportFactory mockTransportFactory;
  @Mock private LoadBalancer mockLoadBalancer;
  @Mock private SubchannelStateListener subchannelStateListener;
  private final LoadBalancerProvider mockLoadBalancerProvider =
      mock(LoadBalancerProvider.class, delegatesTo(new LoadBalancerProvider() {
          @Override
          public LoadBalancer newLoadBalancer(Helper helper) {
            return mockLoadBalancer;
          }

          @Override
          public boolean isAvailable() {
            return true;
          }

          @Override
          public int getPriority() {
            return 999;
          }

          @Override
          public String getPolicyName() {
            return MOCK_POLICY_NAME;
          }
        }));

  @Mock private NameResolver mockNameResolver;
  @Mock private NameResolver.Factory mockNameResolverFactory;
  @Mock private ClientCall.Listener<Integer> mockCallListener;
  @Mock private ClientCall.Listener<Integer> mockCallListener2;
  @Captor private ArgumentCaptor<NameResolver.Listener2> nameResolverListenerCaptor;
  private BlockingQueue<MockClientTransportInfo> newTransports;

  @Before
  @SuppressWarnings("deprecation") // For NameResolver.Listener
  public void setUp() {
    when(mockLoadBalancer.acceptResolvedAddresses(isA(ResolvedAddresses.class))).thenReturn(
        Status.OK);
    LoadBalancerRegistry.getDefaultRegistry().register(mockLoadBalancerProvider);
    when(mockNameResolver.getServiceAuthority()).thenReturn(AUTHORITY);
    when(mockNameResolverFactory
        .newNameResolver(any(URI.class), any(NameResolver.Args.class)))
        .thenReturn(mockNameResolver);
    when(mockNameResolverFactory.getDefaultScheme())
        .thenReturn("mockscheme");
    when(mockTransportFactory.getScheduledExecutorService())
        .thenReturn(timer.getScheduledExecutorService());
    when(mockTransportFactory.getSupportedSocketAddressTypes())
        .thenReturn(Collections.singleton(InetSocketAddress.class));

    String target = "mockscheme:///target";
    URI targetUri = URI.create(target);
    ManagedChannelImplBuilder builder = new ManagedChannelImplBuilder(target,
        new UnsupportedClientTransportFactoryBuilder(), null);

    builder
        .nameResolverFactory(mockNameResolverFactory)
        .defaultLoadBalancingPolicy(MOCK_POLICY_NAME)
        .idleTimeout(IDLE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .userAgent(USER_AGENT);
    builder.executorPool = executorPool;
    NameResolverProvider nameResolverProvider =
        builder.nameResolverRegistry.getProviderForScheme(targetUri.getScheme());
    channel = new ManagedChannelImpl(
        builder, mockTransportFactory, targetUri, nameResolverProvider,
        new FakeBackoffPolicyProvider(),
        oobExecutorPool, timer.getStopwatchSupplier(),
        Collections.<ClientInterceptor>emptyList(),
        TimeProvider.SYSTEM_TIME_PROVIDER);
    newTransports = TestUtils.captureTransports(mockTransportFactory);

    for (int i = 0; i < 2; i++) {
      ArrayList<SocketAddress> addrs = Lists.newArrayList();
      for (int j = 0; j < 2; j++) {
        addrs.add(new FakeSocketAddress("servergroup" + i + "server" + j));
      }
      servers.add(new EquivalentAddressGroup(addrs));
    }
    verify(mockNameResolverFactory).newNameResolver(any(URI.class), any(NameResolver.Args.class));
    // Verify the initial idleness
    verify(mockLoadBalancerProvider, never()).newLoadBalancer(any(Helper.class));
    verify(mockTransportFactory, never()).newClientTransport(
        any(SocketAddress.class),
        any(ClientTransportFactory.ClientTransportOptions.class),
        any(ChannelLogger.class));
    verify(mockNameResolver, never()).start(any(NameResolver.Listener.class));
    verify(mockNameResolver, never()).start(any(NameResolver.Listener2.class));
  }

  @After
  public void allPendingTasksAreRun() {
    Collection<ScheduledTask> pendingTimerTasks = timer.getPendingTasks();
    for (ScheduledTask a : pendingTimerTasks) {
      assertFalse(Rescheduler.isEnabled(a.command));
    }
    assertEquals(executor.getPendingTasks() + " should be empty", 0, executor.numPendingTasks());
  }

  @After
  public void cleanUp() {
    LoadBalancerRegistry.getDefaultRegistry().deregister(mockLoadBalancerProvider);
  }

  @Test
  public void newCallExitsIdleness() throws Exception {
    ClientCall<String, Integer> call = channel.newCall(method, CallOptions.DEFAULT);
    call.start(mockCallListener, new Metadata());

    verify(mockLoadBalancerProvider).newLoadBalancer(any(Helper.class));
    deliverResolutionResult();

    ArgumentCaptor<ResolvedAddresses> resolvedAddressCaptor =
        ArgumentCaptor.forClass(ResolvedAddresses.class);
    verify(mockLoadBalancer).acceptResolvedAddresses(resolvedAddressCaptor.capture());
    assertThat(resolvedAddressCaptor.getValue().getAddresses())
        .containsExactlyElementsIn(servers);
  }

  @Test
  public void newCallRefreshesIdlenessTimer() throws Exception {
    // First call to exit the initial idleness, then immediately cancel the call.
    ClientCall<String, Integer> call = channel.newCall(method, CallOptions.DEFAULT);
    call.start(mockCallListener, new Metadata());
    call.cancel("For testing", null);

    // Verify that we have exited the idle mode
    verify(mockLoadBalancerProvider).newLoadBalancer(any(Helper.class));
    deliverResolutionResult();
    assertFalse(channel.inUseStateAggregator.isInUse());
    verify(mockCallListener).onClose(any(Status.class), any(Metadata.class));

    // Move closer to idleness, but not yet.
    timer.forwardTime(IDLE_TIMEOUT_SECONDS - 1, TimeUnit.SECONDS);
    verify(mockLoadBalancer, never()).shutdown();
    assertFalse(channel.inUseStateAggregator.isInUse());

    // A new call would refresh the timer
    call = channel.newCall(method, CallOptions.DEFAULT);
    call.start(mockCallListener, new Metadata());
    call.cancel("For testing", null);
    assertFalse(channel.inUseStateAggregator.isInUse());

    // ... so that passing the same length of time will not trigger idle mode
    timer.forwardTime(IDLE_TIMEOUT_SECONDS - 1, TimeUnit.SECONDS);
    verify(mockLoadBalancer, never()).shutdown();
    assertFalse(channel.inUseStateAggregator.isInUse());

    // ... until the time since last call has reached the timeout
    timer.forwardTime(1, TimeUnit.SECONDS);
    verify(mockLoadBalancer).shutdown();
    assertFalse(channel.inUseStateAggregator.isInUse());

    executor.runDueTasks();
    verify(mockCallListener, times(2)).onClose(any(Status.class), any(Metadata.class));
  }

  @Test
  public void delayedTransportHoldsOffIdleness() throws Exception {
    ClientCall<String, Integer> call = channel.newCall(method, CallOptions.DEFAULT);
    call.start(mockCallListener, new Metadata());
    deliverResolutionResult();
    assertTrue(channel.inUseStateAggregator.isInUse());

    // As long as the delayed transport is in-use (by the pending RPC), the channel won't go idle.
    timer.forwardTime(IDLE_TIMEOUT_SECONDS * 2, TimeUnit.SECONDS);
    assertTrue(channel.inUseStateAggregator.isInUse());

    // Cancelling the only RPC will reset the in-use state.
    assertEquals(0, executor.numPendingTasks());
    call.cancel("In test", null);
    assertEquals(1, executor.runDueTasks());
    assertFalse(channel.inUseStateAggregator.isInUse());
    // And allow the channel to go idle.
    timer.forwardTime(IDLE_TIMEOUT_SECONDS - 1, TimeUnit.SECONDS);
    verify(mockLoadBalancer, never()).shutdown();
    timer.forwardTime(1, TimeUnit.SECONDS);
    verify(mockLoadBalancer).shutdown();
  }

  @Test
  public void pendingCallExitsIdleAfterEnter() throws Exception {
    // Create a pending call without starting it.
    channel.newCall(method, CallOptions.DEFAULT);

    channel.enterIdle();

    // Just the existence of a non-started, pending call means the channel cannot stay
    // in idle mode because the expectation is that the pending call will also need to
    // be handled.
    verify(mockNameResolver, times(2)).start(any(NameResolver.Listener2.class));
  }

  @Test
  public void delayedTransportExitsIdleAfterEnter() throws Exception {
    // Start a new call that will go to the delayed transport
    ClientCall<String, Integer> call = channel.newCall(method, CallOptions.DEFAULT);
    call.start(mockCallListener, new Metadata());
    deliverResolutionResult();

    channel.enterIdle();

    // Since we have a call in delayed transport, the call to enterIdle() should have resulted in
    // the channel going to idle mode and then immediately exiting. We confirm this by verifying
    // that the name resolver was started up twice - once when the call was first created and a
    // second time after exiting idle mode.
    verify(mockNameResolver, times(2)).start(any(NameResolver.Listener2.class));
  }

  @Test
  public void realTransportsHoldsOffIdleness() throws Exception {
    final EquivalentAddressGroup addressGroup = servers.get(1);

    // Start a call, which goes to delayed transport
    ClientCall<String, Integer> call = channel.newCall(method, CallOptions.DEFAULT);
    call.start(mockCallListener, new Metadata());

    // Verify that we have exited the idle mode
    ArgumentCaptor<Helper> helperCaptor = ArgumentCaptor.forClass(Helper.class);
    verify(mockLoadBalancerProvider).newLoadBalancer(helperCaptor.capture());
    deliverResolutionResult();
    Helper helper = helperCaptor.getValue();
    assertTrue(channel.inUseStateAggregator.isInUse());

    // Assume LoadBalancer has received an address, then create a subchannel.
    Subchannel subchannel = createSubchannelSafely(helper, addressGroup, Attributes.EMPTY);
    requestConnectionSafely(helper, subchannel);
    MockClientTransportInfo t0 = newTransports.poll();
    t0.listener.transportReady();

    SubchannelPicker mockPicker = mock(SubchannelPicker.class);
    when(mockPicker.pickSubchannel(any(PickSubchannelArgs.class)))
        .thenReturn(PickResult.withSubchannel(subchannel));
    updateBalancingStateSafely(helper, READY, mockPicker);
    // Delayed transport creates real streams in the app executor
    executor.runDueTasks();

    // Delayed transport exits in-use, while real transport has not entered in-use yet.
    assertFalse(channel.inUseStateAggregator.isInUse());

    // Now it's in-use
    t0.listener.transportInUse(true);
    assertTrue(channel.inUseStateAggregator.isInUse());

    // As long as the transport is in-use, the channel won't go idle.
    timer.forwardTime(IDLE_TIMEOUT_SECONDS * 2, TimeUnit.SECONDS);
    assertTrue(channel.inUseStateAggregator.isInUse());

    t0.listener.transportInUse(false);
    assertFalse(channel.inUseStateAggregator.isInUse());
    // And allow the channel to go idle.
    timer.forwardTime(IDLE_TIMEOUT_SECONDS - 1, TimeUnit.SECONDS);
    verify(mockLoadBalancer, never()).shutdown();
    timer.forwardTime(1, TimeUnit.SECONDS);
    verify(mockLoadBalancer).shutdown();
  }

  @Test
  public void enterIdleWhileRealTransportInProgress() {
    final EquivalentAddressGroup addressGroup = servers.get(1);

    // Start a call, which goes to delayed transport
    ClientCall<String, Integer> call = channel.newCall(method, CallOptions.DEFAULT);
    call.start(mockCallListener, new Metadata());

    // Verify that we have exited the idle mode
    ArgumentCaptor<Helper> helperCaptor = ArgumentCaptor.forClass(Helper.class);
    verify(mockLoadBalancerProvider).newLoadBalancer(helperCaptor.capture());
    deliverResolutionResult();
    Helper helper = helperCaptor.getValue();

    // Create a subchannel for the real transport to happen on.
    Subchannel subchannel = createSubchannelSafely(helper, addressGroup, Attributes.EMPTY);
    requestConnectionSafely(helper, subchannel);
    MockClientTransportInfo t0 = newTransports.poll();
    t0.listener.transportReady();

    SubchannelPicker mockPicker = mock(SubchannelPicker.class);
    when(mockPicker.pickSubchannel(any(PickSubchannelArgs.class)))
            .thenReturn(PickResult.withSubchannel(subchannel));
    updateBalancingStateSafely(helper, READY, mockPicker);

    // Delayed transport creates real streams in the app executor
    executor.runDueTasks();

    // Move transport to the in-use state
    t0.listener.transportInUse(true);

    // Now we enter Idle mode while real transport is happening
    channel.enterIdle();

    // Verify that the name resolver and the load balance were shut down.
    verify(mockNameResolver).shutdown();
    verify(mockLoadBalancer).shutdown();

    // When there are no pending streams, the call to enterIdle() should stick and
    // we remain in idle mode. We verify this by making sure that the name resolver
    // was not started up more than once (the initial startup).
    verify(mockNameResolver, atMostOnce()).start(isA(NameResolver.Listener2.class));
  }

  @Test
  public void updateSubchannelAddresses_newAddressConnects() {
    ClientCall<String, Integer> call = channel.newCall(method, CallOptions.DEFAULT);
    call.start(mockCallListener, new Metadata()); // Create LB
    ArgumentCaptor<Helper> helperCaptor = ArgumentCaptor.forClass(Helper.class);
    verify(mockLoadBalancerProvider).newLoadBalancer(helperCaptor.capture());
    deliverResolutionResult();
    Helper helper = helperCaptor.getValue();
    Subchannel subchannel = createSubchannelSafely(helper, servers.get(0), Attributes.EMPTY);

    requestConnectionSafely(helper, subchannel);
    MockClientTransportInfo t0 = newTransports.poll();
    t0.listener.transportReady();

    updateSubchannelAddressesSafely(helper, subchannel, servers.get(1));

    requestConnectionSafely(helper, subchannel);
    MockClientTransportInfo t1 = newTransports.poll();
    t1.listener.transportReady();

    // Drain InternalSubchannel's delayed shutdown on updateAddresses
    timer.forwardTime(ManagedChannelImpl.SUBCHANNEL_SHUTDOWN_DELAY_SECONDS, TimeUnit.SECONDS);
  }

  @Test
  public void updateSubchannelAddresses_existingAddressDoesNotConnect() {
    ClientCall<String, Integer> call = channel.newCall(method, CallOptions.DEFAULT);
    call.start(mockCallListener, new Metadata()); // Create LB
    ArgumentCaptor<Helper> helperCaptor = ArgumentCaptor.forClass(Helper.class);
    verify(mockLoadBalancerProvider).newLoadBalancer(helperCaptor.capture());
    deliverResolutionResult();
    Helper helper = helperCaptor.getValue();
    Subchannel subchannel = createSubchannelSafely(helper, servers.get(0), Attributes.EMPTY);

    requestConnectionSafely(helper, subchannel);
    MockClientTransportInfo t0 = newTransports.poll();
    t0.listener.transportReady();

    List<SocketAddress> changedList = new ArrayList<>(servers.get(0).getAddresses());
    changedList.add(new FakeSocketAddress("aDifferentServer"));
    updateSubchannelAddressesSafely(helper, subchannel, new EquivalentAddressGroup(changedList));

    requestConnectionSafely(helper, subchannel);
    assertNull(newTransports.poll());
  }

  @Test
  public void oobTransportDoesNotAffectIdleness() {
    // Start a call, which goes to delayed transport
    ClientCall<String, Integer> call = channel.newCall(method, CallOptions.DEFAULT);
    call.start(mockCallListener, new Metadata());

    // Verify that we have exited the idle mode
    ArgumentCaptor<Helper> helperCaptor = ArgumentCaptor.forClass(Helper.class);
    verify(mockLoadBalancerProvider).newLoadBalancer(helperCaptor.capture());
    Helper helper = helperCaptor.getValue();
    deliverResolutionResult();

    // Fail the RPC
    SubchannelPicker failingPicker = mock(SubchannelPicker.class);
    when(failingPicker.pickSubchannel(any(PickSubchannelArgs.class)))
        .thenReturn(PickResult.withError(Status.UNAVAILABLE));
    updateBalancingStateSafely(helper, TRANSIENT_FAILURE, failingPicker);
    executor.runDueTasks();
    verify(mockCallListener).onClose(same(Status.UNAVAILABLE), any(Metadata.class));

    // ... so that the channel resets its in-use state
    assertFalse(channel.inUseStateAggregator.isInUse());

    // Now make an RPC on an OOB channel
    ManagedChannel oob = helper.createOobChannel(servers, "oobauthority");
    verify(mockTransportFactory, never())
        .newClientTransport(
            any(SocketAddress.class),
            eq(new ClientTransportFactory.ClientTransportOptions()
              .setAuthority("oobauthority")
              .setUserAgent(USER_AGENT)),
            any(ChannelLogger.class));
    ClientCall<String, Integer> oobCall = oob.newCall(method, CallOptions.DEFAULT);
    oobCall.start(mockCallListener2, new Metadata());
    verify(mockTransportFactory)
        .newClientTransport(
            any(SocketAddress.class),
            eq(new ClientTransportFactory.ClientTransportOptions()
              .setAuthority("oobauthority")
              .setUserAgent(USER_AGENT)),
            any(ChannelLogger.class));
    MockClientTransportInfo oobTransportInfo = newTransports.poll();
    assertEquals(0, newTransports.size());
    // The OOB transport reports in-use state
    oobTransportInfo.listener.transportInUse(true);

    // But it won't stop the channel from going idle
    verify(mockLoadBalancer, never()).shutdown();
    timer.forwardTime(IDLE_TIMEOUT_SECONDS, TimeUnit.SECONDS);
    verify(mockLoadBalancer).shutdown();
  }

  @Test
  public void updateOobChannelAddresses_newAddressConnects() {
    ClientCall<String, Integer> call = channel.newCall(method, CallOptions.DEFAULT);
    call.start(mockCallListener, new Metadata()); // Create LB
    ArgumentCaptor<Helper> helperCaptor = ArgumentCaptor.forClass(Helper.class);
    verify(mockLoadBalancerProvider).newLoadBalancer(helperCaptor.capture());
    deliverResolutionResult();
    Helper helper = helperCaptor.getValue();
    ManagedChannel oobChannel = helper.createOobChannel(servers.subList(0,1), "localhost");

    oobChannel.newCall(method, CallOptions.DEFAULT).start(mockCallListener, new Metadata());
    MockClientTransportInfo t0 = newTransports.poll();
    t0.listener.transportReady();

    helper.updateOobChannelAddresses(oobChannel, servers.subList(1,2));

    oobChannel.newCall(method, CallOptions.DEFAULT).start(mockCallListener, new Metadata());
    MockClientTransportInfo t1 = newTransports.poll();
    t1.listener.transportReady();

    // Drain InternalSubchannel's delayed shutdown on updateAddresses
    timer.forwardTime(ManagedChannelImpl.SUBCHANNEL_SHUTDOWN_DELAY_SECONDS, TimeUnit.SECONDS);
  }

  @Test
  public void updateOobChannelAddresses_existingAddressDoesNotConnect() {
    ClientCall<String, Integer> call = channel.newCall(method, CallOptions.DEFAULT);
    call.start(mockCallListener, new Metadata()); // Create LB
    ArgumentCaptor<Helper> helperCaptor = ArgumentCaptor.forClass(Helper.class);
    verify(mockLoadBalancerProvider).newLoadBalancer(helperCaptor.capture());
    Helper helper = helperCaptor.getValue();
    deliverResolutionResult();
    ManagedChannel oobChannel = helper.createOobChannel(servers.subList(0,1), "localhost");

    oobChannel.newCall(method, CallOptions.DEFAULT).start(mockCallListener, new Metadata());
    MockClientTransportInfo t0 = newTransports.poll();
    t0.listener.transportReady();

    List<SocketAddress> changedList = new ArrayList<>(servers.get(0).getAddresses());
    changedList.add(new FakeSocketAddress("aDifferentServer"));
    helper.updateOobChannelAddresses(oobChannel, Collections.singletonList(
        new EquivalentAddressGroup(changedList)));

    oobChannel.newCall(method, CallOptions.DEFAULT).start(mockCallListener, new Metadata());
    assertNull(newTransports.poll());
  }

  private static class FakeBackoffPolicyProvider implements BackoffPolicy.Provider {
    @Override
    public BackoffPolicy get() {
      return new BackoffPolicy() {
        @Override
        public long nextBackoffNanos() {
          return 1;
        }
      };
    }
  }

  private static class FakeSocketAddress extends SocketAddress {
    final String name;

    FakeSocketAddress(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return "FakeSocketAddress-" + name;
    }
  }

  // Helper methods to call methods from SynchronizationContext
  private Subchannel createSubchannelSafely(
      final Helper helper, final EquivalentAddressGroup addressGroup, final Attributes attrs) {
    final AtomicReference<Subchannel> resultCapture = new AtomicReference<>();
    helper.getSynchronizationContext().execute(
        new Runnable() {
          @Override
          public void run() {
            Subchannel s = helper.createSubchannel(CreateSubchannelArgs.newBuilder()
                .setAddresses(addressGroup)
                .setAttributes(attrs)
                .build());
            s.start(subchannelStateListener);
            resultCapture.set(s);
          }
        });
    return resultCapture.get();
  }

  private void deliverResolutionResult() {
    verify(mockNameResolver).start(nameResolverListenerCaptor.capture());
    // Simulate new address resolved to make sure the LoadBalancer is correctly linked to
    // the NameResolver.
    ResolutionResult resolutionResult =
        ResolutionResult.newBuilder()
            .setAddressesOrError(StatusOr.fromValue(servers))
            .setAttributes(Attributes.EMPTY)
            .build();
    nameResolverListenerCaptor.getValue().onResult(resolutionResult);
    executor.runDueTasks();
  }

  private static void requestConnectionSafely(Helper helper, final Subchannel subchannel) {
    helper.getSynchronizationContext().execute(
        new Runnable() {
          @Override
          public void run() {
            subchannel.requestConnection();
          }
        });
  }

  private static void updateBalancingStateSafely(
      final Helper helper, final ConnectivityState state, final SubchannelPicker picker) {
    helper.getSynchronizationContext().execute(
        new Runnable() {
          @Override
          public void run() {
            helper.updateBalancingState(state, picker);
          }
        });
  }

  private static void updateSubchannelAddressesSafely(
      final Helper helper, final Subchannel subchannel, final EquivalentAddressGroup addrs) {
    helper.getSynchronizationContext().execute(
        new Runnable() {
          @Override
          public void run() {
            subchannel.updateAddresses(Collections.singletonList(addrs));
          }
        });
  }
}
