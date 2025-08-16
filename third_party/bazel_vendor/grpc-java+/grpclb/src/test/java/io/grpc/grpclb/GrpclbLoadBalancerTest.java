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

package io.grpc.grpclb;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.truth.Truth.assertThat;
import static io.grpc.ConnectivityState.CONNECTING;
import static io.grpc.ConnectivityState.IDLE;
import static io.grpc.ConnectivityState.READY;
import static io.grpc.ConnectivityState.SHUTDOWN;
import static io.grpc.ConnectivityState.TRANSIENT_FAILURE;
import static io.grpc.grpclb.GrpclbState.BUFFER_ENTRY;
import static io.grpc.grpclb.GrpclbState.DROP_PICK_RESULT;
import static io.grpc.grpclb.GrpclbState.NO_USE_AUTHORITY_SUFFIX;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.AdditionalAnswers.delegatesTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.google.common.collect.Iterables;
import com.google.protobuf.ByteString;
import com.google.protobuf.util.Durations;
import com.google.protobuf.util.Timestamps;
import io.grpc.Attributes;
import io.grpc.ChannelLogger;
import io.grpc.ClientStreamTracer;
import io.grpc.ConnectivityState;
import io.grpc.ConnectivityStateInfo;
import io.grpc.Context;
import io.grpc.Context.CancellableContext;
import io.grpc.EquivalentAddressGroup;
import io.grpc.LoadBalancer.CreateSubchannelArgs;
import io.grpc.LoadBalancer.Helper;
import io.grpc.LoadBalancer.PickResult;
import io.grpc.LoadBalancer.PickSubchannelArgs;
import io.grpc.LoadBalancer.ResolvedAddresses;
import io.grpc.LoadBalancer.Subchannel;
import io.grpc.LoadBalancer.SubchannelPicker;
import io.grpc.LoadBalancer.SubchannelStateListener;
import io.grpc.ManagedChannel;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.Status.Code;
import io.grpc.SynchronizationContext;
import io.grpc.grpclb.GrpclbState.BackendEntry;
import io.grpc.grpclb.GrpclbState.DropEntry;
import io.grpc.grpclb.GrpclbState.ErrorEntry;
import io.grpc.grpclb.GrpclbState.IdleSubchannelEntry;
import io.grpc.grpclb.GrpclbState.Mode;
import io.grpc.grpclb.GrpclbState.RoundRobinPicker;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.internal.BackoffPolicy;
import io.grpc.internal.FakeClock;
import io.grpc.lb.v1.ClientStats;
import io.grpc.lb.v1.ClientStatsPerToken;
import io.grpc.lb.v1.FallbackResponse;
import io.grpc.lb.v1.InitialLoadBalanceRequest;
import io.grpc.lb.v1.InitialLoadBalanceResponse;
import io.grpc.lb.v1.LoadBalanceRequest;
import io.grpc.lb.v1.LoadBalanceResponse;
import io.grpc.lb.v1.LoadBalancerGrpc;
import io.grpc.lb.v1.Server;
import io.grpc.lb.v1.ServerList;
import io.grpc.stub.StreamObserver;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.text.MessageFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

/** Unit tests for {@link GrpclbLoadBalancer}. */
@RunWith(JUnit4.class)
public class GrpclbLoadBalancerTest {
  @Rule public final MockitoRule mocks = MockitoJUnit.rule();

  private static final String SERVICE_AUTHORITY = "api.google.com";

  // The tasks are wrapped by SynchronizationContext, so we can't compare the types
  // directly.
  private static final FakeClock.TaskFilter LOAD_REPORTING_TASK_FILTER =
      new FakeClock.TaskFilter() {
        @Override
        public boolean shouldAccept(Runnable command) {
          return command.toString().contains(GrpclbState.LoadReportingTask.class.getSimpleName());
        }
      };
  private static final FakeClock.TaskFilter FALLBACK_MODE_TASK_FILTER =
      new FakeClock.TaskFilter() {
        @Override
        public boolean shouldAccept(Runnable command) {
          return command.toString().contains(GrpclbState.FallbackModeTask.class.getSimpleName());
        }
      };
  private static final FakeClock.TaskFilter LB_RPC_RETRY_TASK_FILTER =
      new FakeClock.TaskFilter() {
        @Override
        public boolean shouldAccept(Runnable command) {
          return command.toString().contains(GrpclbState.LbRpcRetryTask.class.getSimpleName());
        }
      };
  private static final Attributes LB_BACKEND_ATTRS =
      Attributes.newBuilder().set(GrpclbConstants.ATTR_LB_PROVIDED_BACKEND, true).build();

  private final Helper helper = mock(Helper.class, delegatesTo(new FakeHelper()));
  private final SubchannelPool subchannelPool =
      mock(
          SubchannelPool.class,
          delegatesTo(new CachedSubchannelPool(helper)));
  private final ArrayList<String> logs = new ArrayList<>();
  private final ChannelLogger channelLogger = new ChannelLogger() {
      @Override
      public void log(ChannelLogLevel level, String msg) {
        logs.add(level + ": " + msg);
      }

      @Override
      public void log(ChannelLogLevel level, String template, Object... args) {
        log(level, MessageFormat.format(template, args));
      }
    };
  private SubchannelPicker currentPicker;
  private LoadBalancerGrpc.LoadBalancerImplBase mockLbService;
  @Captor
  private ArgumentCaptor<StreamObserver<LoadBalanceResponse>> lbResponseObserverCaptor;
  private final FakeClock fakeClock = new FakeClock();
  private final ArrayDeque<StreamObserver<LoadBalanceRequest>> lbRequestObservers =
      new ArrayDeque<>();
  private final ArrayDeque<Subchannel> mockSubchannels = new ArrayDeque<>();
  private final ArrayDeque<ManagedChannel> fakeOobChannels = new ArrayDeque<>();
  private final ArrayList<Subchannel> unpooledSubchannelTracker = new ArrayList<>();
  private final ArrayList<ManagedChannel> oobChannelTracker = new ArrayList<>();
  private final SynchronizationContext syncContext = new SynchronizationContext(
      new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
          throw new AssertionError(e);
        }
      });
  private static final ClientStreamTracer.StreamInfo STREAM_INFO =
      ClientStreamTracer.StreamInfo.newBuilder().build();

  private io.grpc.Server fakeLbServer;
  @Captor
  private ArgumentCaptor<SubchannelPicker> pickerCaptor;
  @Mock
  private BackoffPolicy.Provider backoffPolicyProvider;
  @Mock
  private BackoffPolicy backoffPolicy1;
  @Mock
  private BackoffPolicy backoffPolicy2;
  private GrpclbLoadBalancer balancer;
  private final ArgumentCaptor<CreateSubchannelArgs> createSubchannelArgsCaptor =
      ArgumentCaptor.forClass(CreateSubchannelArgs.class);

  @Before
  public void setUp() throws Exception {
    mockLbService = mock(LoadBalancerGrpc.LoadBalancerImplBase.class, delegatesTo(
        new LoadBalancerGrpc.LoadBalancerImplBase() {
          @Override
          @SuppressWarnings("unchecked")
          public StreamObserver<LoadBalanceRequest> balanceLoad(
              final StreamObserver<LoadBalanceResponse> responseObserver) {
            StreamObserver<LoadBalanceRequest> requestObserver =
                mock(StreamObserver.class);
            Answer<Void> closeRpc = new Answer<Void>() {
                @Override
                public Void answer(InvocationOnMock invocation) {
                  responseObserver.onCompleted();
                  return null;
                }
              };
            doAnswer(closeRpc).when(requestObserver).onCompleted();
            lbRequestObservers.add(requestObserver);
            return requestObserver;
          }
        }));
    fakeLbServer = InProcessServerBuilder.forName("fakeLb")
        .directExecutor().addService(mockLbService).build().start();
    when(backoffPolicy1.nextBackoffNanos()).thenReturn(10L, 100L);
    when(backoffPolicy2.nextBackoffNanos()).thenReturn(10L, 100L);
    when(backoffPolicyProvider.get()).thenReturn(backoffPolicy1, backoffPolicy2);
    balancer = new GrpclbLoadBalancer(
        helper,
        Context.ROOT,
        subchannelPool,
        fakeClock.getTimeProvider(),
        fakeClock.getStopwatchSupplier().get(),
        backoffPolicyProvider);
  }

  @After
  public void tearDown() {
    try {
      if (balancer != null) {
        syncContext.execute(new Runnable() {
            @Override
            public void run() {
              balancer.shutdown();
            }
          });
      }
      for (ManagedChannel channel : oobChannelTracker) {
        assertTrue(channel + " is shutdown", channel.isShutdown());
        // balancer should have closed the LB stream, terminating the OOB channel.
        assertTrue(channel + " is terminated", channel.isTerminated());
      }
      for (Subchannel subchannel : unpooledSubchannelTracker) {
        verify(subchannel).shutdown();
      }
      // No timer should linger after shutdown
      assertThat(fakeClock.getPendingTasks()).isEmpty();
    } finally {
      if (fakeLbServer != null) {
        fakeLbServer.shutdownNow();
      }
    }
  }

  @Test
  public void roundRobinPickerNoDrop() {
    GrpclbClientLoadRecorder loadRecorder =
        new GrpclbClientLoadRecorder(fakeClock.getTimeProvider());
    Subchannel subchannel = mock(Subchannel.class);
    BackendEntry b1 = new BackendEntry(subchannel, loadRecorder, "LBTOKEN0001");
    BackendEntry b2 = new BackendEntry(subchannel, loadRecorder, "LBTOKEN0002");

    List<BackendEntry> pickList = Arrays.asList(b1, b2);
    RoundRobinPicker picker = new RoundRobinPicker(Collections.<DropEntry>emptyList(), pickList);

    PickSubchannelArgs args1 = mock(PickSubchannelArgs.class);
    Metadata headers1 = new Metadata();
    // The existing token on the headers will be replaced
    headers1.put(GrpclbConstants.TOKEN_METADATA_KEY, "LBTOKEN__OLD");
    when(args1.getHeaders()).thenReturn(headers1);
    assertSame(b1.result, picker.pickSubchannel(args1));
    verify(args1).getHeaders();
    assertThat(headers1.getAll(GrpclbConstants.TOKEN_METADATA_KEY)).containsExactly("LBTOKEN0001");

    PickSubchannelArgs args2 = mock(PickSubchannelArgs.class);
    Metadata headers2 = new Metadata();
    when(args2.getHeaders()).thenReturn(headers2);
    assertSame(b2.result, picker.pickSubchannel(args2));
    verify(args2).getHeaders();
    assertThat(headers2.getAll(GrpclbConstants.TOKEN_METADATA_KEY)).containsExactly("LBTOKEN0002");

    PickSubchannelArgs args3 = mock(PickSubchannelArgs.class);
    Metadata headers3 = new Metadata();
    when(args3.getHeaders()).thenReturn(headers3);
    assertSame(b1.result, picker.pickSubchannel(args3));
    verify(args3).getHeaders();
    assertThat(headers3.getAll(GrpclbConstants.TOKEN_METADATA_KEY)).containsExactly("LBTOKEN0001");

    verify(subchannel, never()).getAttributes();
  }

  @Test
  public void roundRobinPickerWithDrop() {
    assertTrue(DROP_PICK_RESULT.isDrop());
    GrpclbClientLoadRecorder loadRecorder =
        new GrpclbClientLoadRecorder(fakeClock.getTimeProvider());
    Subchannel subchannel = mock(Subchannel.class);
    // 1 out of 2 requests are to be dropped
    DropEntry d = new DropEntry(loadRecorder, "LBTOKEN0003");
    List<DropEntry> dropList = Arrays.asList(null, d);

    BackendEntry b1 = new BackendEntry(subchannel, loadRecorder, "LBTOKEN0001");
    BackendEntry b2 = new BackendEntry(subchannel, loadRecorder, "LBTOKEN0002");
    List<BackendEntry> pickList = Arrays.asList(b1, b2);
    RoundRobinPicker picker = new RoundRobinPicker(dropList, pickList);

    // dropList[0], pickList[0]
    PickSubchannelArgs args1 = mock(PickSubchannelArgs.class);
    Metadata headers1 = new Metadata();
    headers1.put(GrpclbConstants.TOKEN_METADATA_KEY, "LBTOKEN__OLD");
    when(args1.getHeaders()).thenReturn(headers1);
    assertSame(b1.result, picker.pickSubchannel(args1));
    verify(args1).getHeaders();
    assertThat(headers1.getAll(GrpclbConstants.TOKEN_METADATA_KEY)).containsExactly("LBTOKEN0001");

    // dropList[1]: drop
    PickSubchannelArgs args2 = mock(PickSubchannelArgs.class);
    Metadata headers2 = new Metadata();
    when(args2.getHeaders()).thenReturn(headers2);
    assertSame(DROP_PICK_RESULT, picker.pickSubchannel(args2));
    verify(args2, never()).getHeaders();

    // dropList[0], pickList[1]
    PickSubchannelArgs args3 = mock(PickSubchannelArgs.class);
    Metadata headers3 = new Metadata();
    when(args3.getHeaders()).thenReturn(headers3);
    assertSame(b2.result, picker.pickSubchannel(args3));
    verify(args3).getHeaders();
    assertThat(headers3.getAll(GrpclbConstants.TOKEN_METADATA_KEY)).containsExactly("LBTOKEN0002");

    // dropList[1]: drop
    PickSubchannelArgs args4 = mock(PickSubchannelArgs.class);
    Metadata headers4 = new Metadata();
    when(args4.getHeaders()).thenReturn(headers4);
    assertSame(DROP_PICK_RESULT, picker.pickSubchannel(args4));
    verify(args4, never()).getHeaders();

    // dropList[0], pickList[0]
    PickSubchannelArgs args5 = mock(PickSubchannelArgs.class);
    Metadata headers5 = new Metadata();
    when(args5.getHeaders()).thenReturn(headers5);
    assertSame(b1.result, picker.pickSubchannel(args5));
    verify(args5).getHeaders();
    assertThat(headers5.getAll(GrpclbConstants.TOKEN_METADATA_KEY)).containsExactly("LBTOKEN0001");

    verify(subchannel, never()).getAttributes();
  }

  @Test
  public void roundRobinPickerWithIdleEntry_noDrop() {
    Subchannel subchannel = mock(Subchannel.class);
    IdleSubchannelEntry entry = new IdleSubchannelEntry(subchannel, syncContext);

    RoundRobinPicker picker =
        new RoundRobinPicker(Collections.<DropEntry>emptyList(), Collections.singletonList(entry));
    PickSubchannelArgs args = mock(PickSubchannelArgs.class);

    verify(subchannel, never()).requestConnection();
    assertThat(picker.pickSubchannel(args)).isSameInstanceAs(PickResult.withNoResult());
    verify(subchannel).requestConnection();
    assertThat(picker.pickSubchannel(args)).isSameInstanceAs(PickResult.withNoResult());
    // Only the first pick triggers requestConnection()
    verify(subchannel).requestConnection();
  }

  @Test
  public void roundRobinPickerWithIdleEntry_andDrop() {
    GrpclbClientLoadRecorder loadRecorder =
        new GrpclbClientLoadRecorder(fakeClock.getTimeProvider());
    // 1 out of 2 requests are to be dropped
    DropEntry d = new DropEntry(loadRecorder, "LBTOKEN0003");
    List<DropEntry> dropList = Arrays.asList(null, d);

    Subchannel subchannel = mock(Subchannel.class);
    IdleSubchannelEntry entry = new IdleSubchannelEntry(subchannel, syncContext);

    RoundRobinPicker picker = new RoundRobinPicker(dropList, Collections.singletonList(entry));
    PickSubchannelArgs args = mock(PickSubchannelArgs.class);

    verify(subchannel, never()).requestConnection();
    assertThat(picker.pickSubchannel(args)).isSameInstanceAs(PickResult.withNoResult());
    verify(subchannel).requestConnection();

    assertThat(picker.pickSubchannel(args)).isSameInstanceAs(DROP_PICK_RESULT);

    verify(subchannel).requestConnection();
    assertThat(picker.pickSubchannel(args)).isSameInstanceAs(PickResult.withNoResult());
    // Only the first pick triggers requestConnection()
    verify(subchannel).requestConnection();
  }

  @Test
  public void loadReporting() {
    Metadata headers = new Metadata();
    PickSubchannelArgs args = mock(PickSubchannelArgs.class);
    when(args.getHeaders()).thenReturn(headers);

    long loadReportIntervalMillis = 1983;
    List<EquivalentAddressGroup> grpclbBalancerList = createResolvedBalancerAddresses(1);
    deliverResolvedAddresses(Collections.<EquivalentAddressGroup>emptyList(), grpclbBalancerList);

    // Fallback timer is started as soon as address is resolved.
    assertEquals(1, fakeClock.numPendingTasks(FALLBACK_MODE_TASK_FILTER));

    assertEquals(1, fakeOobChannels.size());
    verify(mockLbService).balanceLoad(lbResponseObserverCaptor.capture());
    StreamObserver<LoadBalanceResponse> lbResponseObserver = lbResponseObserverCaptor.getValue();
    assertEquals(1, lbRequestObservers.size());
    StreamObserver<LoadBalanceRequest> lbRequestObserver = lbRequestObservers.poll();
    InOrder inOrder = inOrder(lbRequestObserver);
    InOrder helperInOrder = inOrder(helper, subchannelPool);

    inOrder.verify(lbRequestObserver).onNext(
        eq(LoadBalanceRequest.newBuilder().setInitialRequest(
            InitialLoadBalanceRequest.newBuilder().setName(SERVICE_AUTHORITY).build())
            .build()));

    // Simulate receiving LB response
    assertEquals(0, fakeClock.numPendingTasks(LOAD_REPORTING_TASK_FILTER));
    lbResponseObserver.onNext(buildInitialResponse(loadReportIntervalMillis));

    // Load reporting task is scheduled
    assertEquals(1, fakeClock.numPendingTasks(LOAD_REPORTING_TASK_FILTER));
    assertEquals(0, fakeClock.runDueTasks());

    List<ServerEntry> backends = Arrays.asList(
        new ServerEntry("127.0.0.1", 2000, "token0001"),
        new ServerEntry("token0001"),  // drop
        new ServerEntry("127.0.0.1", 2010, "token0002"),
        new ServerEntry("token0003"));  // drop

    lbResponseObserver.onNext(buildLbResponse(backends));

    assertEquals(2, mockSubchannels.size());
    Subchannel subchannel1 = mockSubchannels.poll();
    Subchannel subchannel2 = mockSubchannels.poll();
    deliverSubchannelState(subchannel1, ConnectivityStateInfo.forNonError(CONNECTING));
    deliverSubchannelState(subchannel2, ConnectivityStateInfo.forNonError(CONNECTING));
    deliverSubchannelState(subchannel1, ConnectivityStateInfo.forNonError(READY));
    deliverSubchannelState(subchannel2, ConnectivityStateInfo.forNonError(READY));

    helperInOrder.verify(helper, atLeast(1))
        .updateBalancingState(eq(READY), pickerCaptor.capture());
    RoundRobinPicker picker = (RoundRobinPicker) pickerCaptor.getValue();
    assertThat(picker.dropList).containsExactly(
        null,
        new DropEntry(getLoadRecorder(), "token0001"),
        null,
        new DropEntry(getLoadRecorder(), "token0003")).inOrder();
    assertThat(picker.pickList).containsExactly(
        new BackendEntry(subchannel1, getLoadRecorder(), "token0001"),
        new BackendEntry(subchannel2, getLoadRecorder(), "token0002")).inOrder();

    // Report, no data
    assertNextReport(
        inOrder, lbRequestObserver, loadReportIntervalMillis,
        ClientStats.newBuilder().build());

    PickResult pick1 = picker.pickSubchannel(args);
    assertSame(subchannel1, pick1.getSubchannel());
    assertSame(getLoadRecorder(), pick1.getStreamTracerFactory());

    // Merely the pick will not be recorded as upstart.
    assertNextReport(
        inOrder, lbRequestObserver, loadReportIntervalMillis,
        ClientStats.newBuilder().build());

    ClientStreamTracer tracer1 =
        pick1.getStreamTracerFactory().newClientStreamTracer(STREAM_INFO, new Metadata());
    tracer1.streamCreated(Attributes.EMPTY, new Metadata());

    PickResult pick2 = picker.pickSubchannel(args);
    assertNull(pick2.getSubchannel());
    assertSame(DROP_PICK_RESULT, pick2);

    // Report includes upstart of pick1 and the drop of pick2
    assertNextReport(
        inOrder, lbRequestObserver, loadReportIntervalMillis,
        ClientStats.newBuilder()
            .setNumCallsStarted(2)
            .setNumCallsFinished(1)  // pick2
            .addCallsFinishedWithDrop(
                ClientStatsPerToken.newBuilder()
                    .setLoadBalanceToken("token0001")
                    .setNumCalls(1)          // pick2
                    .build())
            .build());

    PickResult pick3 = picker.pickSubchannel(args);
    assertSame(subchannel2, pick3.getSubchannel());
    assertSame(getLoadRecorder(), pick3.getStreamTracerFactory());
    ClientStreamTracer tracer3 =
        pick3.getStreamTracerFactory().newClientStreamTracer(STREAM_INFO, new Metadata());
    tracer3.streamCreated(Attributes.EMPTY, new Metadata());

    // pick3 has sent out headers
    tracer3.outboundHeaders();

    // 3rd report includes pick3's upstart
    assertNextReport(
        inOrder, lbRequestObserver, loadReportIntervalMillis,
        ClientStats.newBuilder()
            .setNumCallsStarted(1)
            .build());

    PickResult pick4 = picker.pickSubchannel(args);
    assertNull(pick4.getSubchannel());
    assertSame(DROP_PICK_RESULT, pick4);

    // pick1 ended without sending anything
    tracer1.streamClosed(Status.CANCELLED);

    // 4th report includes end of pick1 and drop of pick4
    assertNextReport(
        inOrder, lbRequestObserver, loadReportIntervalMillis,
        ClientStats.newBuilder()
            .setNumCallsStarted(1)  // pick4
            .setNumCallsFinished(2)
            .setNumCallsFinishedWithClientFailedToSend(1)   // pick1
            .addCallsFinishedWithDrop(
                ClientStatsPerToken.newBuilder()
                    .setLoadBalanceToken("token0003")
                    .setNumCalls(1)   // pick4
                    .build())
            .build());

    PickResult pick5 = picker.pickSubchannel(args);
    assertSame(subchannel1, pick1.getSubchannel());
    assertSame(getLoadRecorder(), pick5.getStreamTracerFactory());
    ClientStreamTracer tracer5 =
        pick5.getStreamTracerFactory().newClientStreamTracer(STREAM_INFO, new Metadata());
    tracer5.streamCreated(Attributes.EMPTY, new Metadata());

    // pick3 ended without receiving response headers
    tracer3.streamClosed(Status.DEADLINE_EXCEEDED);

    // pick5 sent and received headers
    tracer5.outboundHeaders();
    tracer5.inboundHeaders();

    // 5th report includes pick3's end and pick5's upstart
    assertNextReport(
        inOrder, lbRequestObserver, loadReportIntervalMillis,
        ClientStats.newBuilder()
            .setNumCallsStarted(1)  // pick5
            .setNumCallsFinished(1)  // pick3
            .build());

    // pick5 ends
    tracer5.streamClosed(Status.OK);

    // 6th report includes pick5's end
    assertNextReport(
        inOrder, lbRequestObserver, loadReportIntervalMillis,
        ClientStats.newBuilder()
            .setNumCallsFinished(1)
            .setNumCallsFinishedKnownReceived(1)
            .build());

    assertEquals(1, fakeClock.numPendingTasks());
    // Balancer closes the stream, scheduled reporting task cancelled
    lbResponseObserver.onError(Status.UNAVAILABLE.asException());
    assertEquals(0, fakeClock.numPendingTasks());

    // New stream created
    verify(mockLbService, times(2)).balanceLoad(lbResponseObserverCaptor.capture());
    lbResponseObserver = lbResponseObserverCaptor.getValue();
    assertEquals(1, lbRequestObservers.size());
    lbRequestObserver = lbRequestObservers.poll();
    inOrder = inOrder(lbRequestObserver);

    inOrder.verify(lbRequestObserver).onNext(
        eq(LoadBalanceRequest.newBuilder().setInitialRequest(
            InitialLoadBalanceRequest.newBuilder().setName(SERVICE_AUTHORITY).build())
            .build()));

    // Load reporting is also requested
    lbResponseObserver.onNext(buildInitialResponse(loadReportIntervalMillis));

    // No picker created because balancer is still using the results from the last stream
    helperInOrder.verify(helper, never())
        .updateBalancingState(any(ConnectivityState.class), any(SubchannelPicker.class));

    // Make a new pick on that picker.  It will not show up on the report of the new stream, because
    // that picker is associated with the previous stream.
    PickResult pick6 = picker.pickSubchannel(args);
    assertNull(pick6.getSubchannel());
    assertSame(DROP_PICK_RESULT, pick6);
    assertNextReport(
        inOrder, lbRequestObserver, loadReportIntervalMillis,
        ClientStats.newBuilder().build());

    // New stream got the list update
    lbResponseObserver.onNext(buildLbResponse(backends));

    // Same backends, thus no new subchannels
    helperInOrder.verify(subchannelPool, never()).takeOrCreateSubchannel(
        any(EquivalentAddressGroup.class), any(Attributes.class));
    // But the new RoundRobinEntries have a new loadRecorder, thus considered different from
    // the previous list, thus a new picker is created
    helperInOrder.verify(helper).updateBalancingState(eq(READY), pickerCaptor.capture());
    picker = (RoundRobinPicker) pickerCaptor.getValue();

    PickResult pick1p = picker.pickSubchannel(args);
    assertSame(subchannel1, pick1p.getSubchannel());
    assertSame(getLoadRecorder(), pick1p.getStreamTracerFactory());
    pick1p.getStreamTracerFactory().newClientStreamTracer(STREAM_INFO, new Metadata());

    // The pick from the new stream will be included in the report
    assertNextReport(
        inOrder, lbRequestObserver, loadReportIntervalMillis,
        ClientStats.newBuilder()
            .setNumCallsStarted(1)
            .build());

    verify(args, atLeast(0)).getHeaders();
    verifyNoMoreInteractions(args);
  }

  @Test
  public void abundantInitialResponse() {
    List<EquivalentAddressGroup> grpclbBalancerList = createResolvedBalancerAddresses(1);
    deliverResolvedAddresses(Collections.<EquivalentAddressGroup>emptyList(), grpclbBalancerList);
    assertEquals(1, fakeOobChannels.size());
    verify(mockLbService).balanceLoad(lbResponseObserverCaptor.capture());
    StreamObserver<LoadBalanceResponse> lbResponseObserver = lbResponseObserverCaptor.getValue();

    // Simulate LB initial response
    assertEquals(0, fakeClock.numPendingTasks(LOAD_REPORTING_TASK_FILTER));
    lbResponseObserver.onNext(buildInitialResponse(1983));

    // Load reporting task is scheduled
    assertEquals(1, fakeClock.numPendingTasks(LOAD_REPORTING_TASK_FILTER));
    FakeClock.ScheduledTask scheduledTask =
        Iterables.getOnlyElement(fakeClock.getPendingTasks(LOAD_REPORTING_TASK_FILTER));
    assertEquals(1983, scheduledTask.getDelay(TimeUnit.MILLISECONDS));

    logs.clear();
    // Simulate an abundant LB initial response, with a different report interval
    lbResponseObserver.onNext(buildInitialResponse(9097));

    // This incident is logged
    assertThat(logs).containsExactly(
            "DEBUG: [grpclb-<api.google.com>] Got an LB response: " + buildInitialResponse(9097),
            "WARNING: [grpclb-<api.google.com>] "
                + "Ignoring unexpected response type: INITIAL_RESPONSE")
        .inOrder();

    // It doesn't affect load-reporting at all
    assertThat(fakeClock.getPendingTasks(LOAD_REPORTING_TASK_FILTER))
        .containsExactly(scheduledTask);
    assertEquals(1983, scheduledTask.getDelay(TimeUnit.MILLISECONDS));
  }

  @SuppressWarnings("unchecked")
  @Test
  public void raceBetweenHandleAddressesAndLbStreamClosure() {
    InOrder inOrder = inOrder(mockLbService, backoffPolicyProvider, backoffPolicy1);
    deliverResolvedAddresses(Collections.<EquivalentAddressGroup>emptyList(),
        createResolvedBalancerAddresses(1));
    assertEquals(1, fakeOobChannels.size());
    inOrder.verify(mockLbService).balanceLoad(lbResponseObserverCaptor.capture());
    StreamObserver<LoadBalanceResponse> lbResponseObserver = lbResponseObserverCaptor.getValue();
    assertEquals(1, lbRequestObservers.size());
    StreamObserver<LoadBalanceRequest> lbRequestObserver = lbRequestObservers.poll();
    verify(lbRequestObserver).onNext(
        eq(LoadBalanceRequest.newBuilder().setInitialRequest(
            InitialLoadBalanceRequest.newBuilder().setName(SERVICE_AUTHORITY).build())
            .build()));

    // Close lbStream
    lbResponseObserver.onCompleted();
    inOrder.verify(backoffPolicyProvider).get();
    inOrder.verify(backoffPolicy1).nextBackoffNanos();
    // Retry task scheduled
    assertEquals(1, fakeClock.numPendingTasks(LB_RPC_RETRY_TASK_FILTER));
    FakeClock.ScheduledTask retryTask =
        Iterables.getOnlyElement(fakeClock.getPendingTasks(LB_RPC_RETRY_TASK_FILTER));
    assertEquals(10L, retryTask.getDelay(TimeUnit.NANOSECONDS));

    // Receive the same Lb address again
    deliverResolvedAddresses(Collections.<EquivalentAddressGroup>emptyList(),
        createResolvedBalancerAddresses(1));
    // Retry task cancelled
    assertEquals(0, fakeClock.numPendingTasks(LB_RPC_RETRY_TASK_FILTER));
    // Reuse the existing OOB channel
    assertEquals(1, fakeOobChannels.size());
    // Start a new LoadBalance RPC
    inOrder.verify(mockLbService).balanceLoad(any(StreamObserver.class));
    assertEquals(1, lbRequestObservers.size());
    lbRequestObserver = lbRequestObservers.poll();
    verify(lbRequestObserver).onNext(
        eq(LoadBalanceRequest.newBuilder().setInitialRequest(
            InitialLoadBalanceRequest.newBuilder().setName(SERVICE_AUTHORITY).build())
            .build()));

    // Simulate a race condition where the task has just started when it's cancelled
    retryTask.command.run();
    inOrder.verifyNoMoreInteractions();
  }

  @Test
  public void raceBetweenLoadReportingAndLbStreamClosure() {
    List<EquivalentAddressGroup> grpclbBalancerList = createResolvedBalancerAddresses(1);
    deliverResolvedAddresses(Collections.<EquivalentAddressGroup>emptyList(), grpclbBalancerList);
    assertEquals(1, fakeOobChannels.size());
    verify(mockLbService).balanceLoad(lbResponseObserverCaptor.capture());
    StreamObserver<LoadBalanceResponse> lbResponseObserver = lbResponseObserverCaptor.getValue();
    assertEquals(1, lbRequestObservers.size());
    StreamObserver<LoadBalanceRequest> lbRequestObserver = lbRequestObservers.poll();
    InOrder inOrder = inOrder(lbRequestObserver);

    inOrder.verify(lbRequestObserver).onNext(
        eq(LoadBalanceRequest.newBuilder().setInitialRequest(
            InitialLoadBalanceRequest.newBuilder().setName(SERVICE_AUTHORITY).build())
            .build()));

    // Simulate receiving LB response
    assertEquals(0, fakeClock.numPendingTasks(LOAD_REPORTING_TASK_FILTER));
    lbResponseObserver.onNext(buildInitialResponse(1983));
    // Load reporting task is scheduled
    assertEquals(1, fakeClock.numPendingTasks(LOAD_REPORTING_TASK_FILTER));
    FakeClock.ScheduledTask scheduledTask =
        Iterables.getOnlyElement(fakeClock.getPendingTasks(LOAD_REPORTING_TASK_FILTER));
    assertEquals(1983, scheduledTask.getDelay(TimeUnit.MILLISECONDS));

    // Close lbStream
    lbResponseObserver.onCompleted();

    // Reporting task cancelled
    assertEquals(0, fakeClock.numPendingTasks(LOAD_REPORTING_TASK_FILTER));

    // Simulate a race condition where the task has just started when its cancelled
    scheduledTask.command.run();

    // No report sent. No new task scheduled
    inOrder.verify(lbRequestObserver, never()).onNext(any(LoadBalanceRequest.class));
    assertEquals(0, fakeClock.numPendingTasks(LOAD_REPORTING_TASK_FILTER));
  }

  private void assertNextReport(
      InOrder inOrder, StreamObserver<LoadBalanceRequest> lbRequestObserver,
      long loadReportIntervalMillis, ClientStats expectedReport) {
    assertEquals(0, fakeClock.forwardTime(loadReportIntervalMillis - 1, TimeUnit.MILLISECONDS));
    inOrder.verifyNoMoreInteractions();
    assertEquals(1, fakeClock.forwardTime(1, TimeUnit.MILLISECONDS));
    assertEquals(1, fakeClock.numPendingTasks());
    inOrder.verify(lbRequestObserver).onNext(
        eq(LoadBalanceRequest.newBuilder()
            .setClientStats(
                ClientStats.newBuilder(expectedReport)
                    .setTimestamp(Timestamps.fromNanos(fakeClock.getTicker().read()))
                    .build())
            .build()));
  }

  @Test
  public void receiveNoBackendAndBalancerAddress() {
    deliverResolvedAddresses(
        Collections.<EquivalentAddressGroup>emptyList(),
        Collections.<EquivalentAddressGroup>emptyList());
    verify(helper).updateBalancingState(eq(TRANSIENT_FAILURE), pickerCaptor.capture());
    RoundRobinPicker picker = (RoundRobinPicker) pickerCaptor.getValue();
    assertThat(picker.dropList).isEmpty();
    Status error = Iterables.getOnlyElement(picker.pickList).picked(new Metadata()).getStatus();
    assertThat(error.getCode()).isEqualTo(Code.UNAVAILABLE);
    assertThat(error.getDescription()).isEqualTo("No backend or balancer addresses found");
  }

  @Test
  public void nameResolutionFailsThenRecover() {
    Status error = Status.NOT_FOUND.withDescription("www.google.com not found");
    deliverNameResolutionError(error);
    verify(helper).updateBalancingState(eq(TRANSIENT_FAILURE), pickerCaptor.capture());
    assertThat(logs)
        .containsExactly(
            "INFO: [grpclb-<api.google.com>] Created",
            "DEBUG: [grpclb-<api.google.com>] Error: " + error)
        .inOrder();
    logs.clear();

    RoundRobinPicker picker = (RoundRobinPicker) pickerCaptor.getValue();
    assertThat(picker.dropList).isEmpty();
    PickResult result = picker.pickSubchannel(mock(PickSubchannelArgs.class));
    assertThat(result.getStatus().getCode()).isEqualTo(Code.UNAVAILABLE);
    assertThat(result.getStatus().getDescription()).isEqualTo(error.getDescription());

    // Recover with a subsequent success
    List<EquivalentAddressGroup> grpclbBalancerList = createResolvedBalancerAddresses(1);

    deliverResolvedAddresses(Collections.<EquivalentAddressGroup>emptyList(), grpclbBalancerList);

    verify(helper).createOobChannel(eq(xattr(grpclbBalancerList)),
        eq(lbAuthority(0) + NO_USE_AUTHORITY_SUFFIX));
    verify(mockLbService).balanceLoad(lbResponseObserverCaptor.capture());
  }

  @Test
  public void grpclbThenNameResolutionFails() {
    InOrder inOrder = inOrder(helper, subchannelPool);
    // Go to GRPCLB first
    List<EquivalentAddressGroup> grpclbBalancerList = createResolvedBalancerAddresses(1);
    deliverResolvedAddresses(Collections.<EquivalentAddressGroup>emptyList(), grpclbBalancerList);

    verify(helper).createOobChannel(eq(xattr(grpclbBalancerList)),
        eq(lbAuthority(0) + NO_USE_AUTHORITY_SUFFIX));
    assertEquals(1, fakeOobChannels.size());
    ManagedChannel oobChannel = fakeOobChannels.poll();
    verify(mockLbService).balanceLoad(lbResponseObserverCaptor.capture());
    StreamObserver<LoadBalanceResponse> lbResponseObserver = lbResponseObserverCaptor.getValue();

    // Let name resolution fail before round-robin list is ready
    Status error = Status.NOT_FOUND.withDescription("www.google.com not found");
    deliverNameResolutionError(error);

    inOrder.verify(helper).updateBalancingState(eq(TRANSIENT_FAILURE), pickerCaptor.capture());
    RoundRobinPicker picker = (RoundRobinPicker) pickerCaptor.getValue();
    assertThat(picker.dropList).isEmpty();
    PickResult result = picker.pickSubchannel(mock(PickSubchannelArgs.class));
    assertThat(result.getStatus().getCode()).isEqualTo(Code.UNAVAILABLE);
    assertThat(result.getStatus().getDescription()).isEqualTo(error.getDescription());
    assertFalse(oobChannel.isShutdown());

    // Simulate receiving LB response
    List<ServerEntry> backends = Arrays.asList(
        new ServerEntry("127.0.0.1", 2000, "TOKEN1"),
        new ServerEntry("127.0.0.1", 2010, "TOKEN2"));
    lbResponseObserver.onNext(buildInitialResponse());
    lbResponseObserver.onNext(buildLbResponse(backends));

    inOrder.verify(subchannelPool).takeOrCreateSubchannel(
        eq(new EquivalentAddressGroup(backends.get(0).addr, LB_BACKEND_ATTRS)),
        any(Attributes.class));
    inOrder.verify(subchannelPool).takeOrCreateSubchannel(
        eq(new EquivalentAddressGroup(backends.get(1).addr, LB_BACKEND_ATTRS)),
        any(Attributes.class));
  }

  @Test
  public void grpclbUpdatedAddresses_avoidsReconnect() {
    List<EquivalentAddressGroup> backendList = createResolvedBackendAddresses(1);
    List<EquivalentAddressGroup> grpclbBalancerList = createResolvedBalancerAddresses(1);
    deliverResolvedAddresses(backendList, grpclbBalancerList);

    verify(helper).createOobChannel(eq(xattr(grpclbBalancerList)),
        eq(lbAuthority(0) + NO_USE_AUTHORITY_SUFFIX));
    ManagedChannel oobChannel = fakeOobChannels.poll();
    assertEquals(1, lbRequestObservers.size());

    List<EquivalentAddressGroup> backendList2 = createResolvedBackendAddresses(1);
    List<EquivalentAddressGroup> grpclbBalancerList2 = createResolvedBalancerAddresses(2);
    deliverResolvedAddresses(backendList2, grpclbBalancerList2);
    verify(helper).updateOobChannelAddresses(eq(oobChannel), eq(xattr(grpclbBalancerList2)));
    assertEquals(1, lbRequestObservers.size()); // No additional RPC
  }


  @Test
  public void grpclbUpdatedAddresses_reconnectOnAuthorityChange() {
    List<EquivalentAddressGroup> backendList = createResolvedBackendAddresses(1);
    List<EquivalentAddressGroup> grpclbBalancerList = createResolvedBalancerAddresses(1);
    deliverResolvedAddresses(backendList, grpclbBalancerList);

    verify(helper).createOobChannel(eq(xattr(grpclbBalancerList)),
        eq(lbAuthority(0) + NO_USE_AUTHORITY_SUFFIX));
    ManagedChannel oobChannel = fakeOobChannels.poll();
    assertEquals(1, lbRequestObservers.size());

    final String newAuthority = "some-new-authority";
    List<EquivalentAddressGroup> backendList2 = createResolvedBackendAddresses(1);
    List<EquivalentAddressGroup> grpclbBalancerList2 =
        Collections.singletonList(
            new EquivalentAddressGroup(
                new FakeSocketAddress("somethingNew"), lbAttributes(newAuthority)));
    deliverResolvedAddresses(backendList2, grpclbBalancerList2);
    verify(helper).updateOobChannelAddresses(eq(oobChannel), eq(xattr(grpclbBalancerList2)));
    assertEquals(1, lbRequestObservers.size()); // No additional RPC
  }

  @Test
  public void grpclbWorking() {
    InOrder inOrder = inOrder(helper, subchannelPool);
    List<EquivalentAddressGroup> grpclbBalancerList = createResolvedBalancerAddresses(1);
    deliverResolvedAddresses(Collections.<EquivalentAddressGroup>emptyList(), grpclbBalancerList);

    // Fallback timer is started as soon as the addresses are resolved.
    assertEquals(1, fakeClock.numPendingTasks(FALLBACK_MODE_TASK_FILTER));

    verify(helper).createOobChannel(eq(xattr(grpclbBalancerList)),
        eq(lbAuthority(0) + NO_USE_AUTHORITY_SUFFIX));
    assertEquals(1, fakeOobChannels.size());
    ManagedChannel oobChannel = fakeOobChannels.poll();
    verify(mockLbService).balanceLoad(lbResponseObserverCaptor.capture());
    StreamObserver<LoadBalanceResponse> lbResponseObserver = lbResponseObserverCaptor.getValue();
    assertEquals(1, lbRequestObservers.size());
    StreamObserver<LoadBalanceRequest> lbRequestObserver = lbRequestObservers.poll();
    verify(lbRequestObserver).onNext(
        eq(LoadBalanceRequest.newBuilder().setInitialRequest(
            InitialLoadBalanceRequest.newBuilder().setName(SERVICE_AUTHORITY).build())
            .build()));

    // Simulate receiving LB response
    List<ServerEntry> backends1 = Arrays.asList(
        new ServerEntry("127.0.0.1", 2000, "token0001"),
        new ServerEntry("127.0.0.1", 2010, "token0002"));
    inOrder.verify(helper, never())
        .updateBalancingState(any(ConnectivityState.class), any(SubchannelPicker.class));
    logs.clear();
    lbResponseObserver.onNext(buildInitialResponse());
    assertThat(logs).containsExactly(
        "INFO: [grpclb-<api.google.com>] Got an LB initial response: " + buildInitialResponse());
    logs.clear();
    lbResponseObserver.onNext(buildLbResponse(backends1));

    inOrder.verify(subchannelPool).takeOrCreateSubchannel(
        eq(new EquivalentAddressGroup(backends1.get(0).addr, LB_BACKEND_ATTRS)),
        any(Attributes.class));
    inOrder.verify(subchannelPool).takeOrCreateSubchannel(
        eq(new EquivalentAddressGroup(backends1.get(1).addr, LB_BACKEND_ATTRS)),
        any(Attributes.class));
    assertEquals(2, mockSubchannels.size());
    Subchannel subchannel1 = mockSubchannels.poll();
    Subchannel subchannel2 = mockSubchannels.poll();
    verify(subchannel1).requestConnection();
    verify(subchannel2).requestConnection();
    assertEquals(
        new EquivalentAddressGroup(backends1.get(0).addr, LB_BACKEND_ATTRS),
        subchannel1.getAddresses());
    assertEquals(
        new EquivalentAddressGroup(backends1.get(1).addr, LB_BACKEND_ATTRS),
        subchannel2.getAddresses());

    deliverSubchannelState(subchannel1, ConnectivityStateInfo.forNonError(CONNECTING));
    deliverSubchannelState(subchannel2, ConnectivityStateInfo.forNonError(CONNECTING));

    inOrder.verify(helper).updateBalancingState(eq(CONNECTING), pickerCaptor.capture());
    RoundRobinPicker picker0 = (RoundRobinPicker) pickerCaptor.getValue();
    assertThat(picker0.dropList).containsExactly(null, null);
    assertThat(picker0.pickList).containsExactly(BUFFER_ENTRY);
    inOrder.verifyNoMoreInteractions();

    assertThat(logs).containsExactly(
        "DEBUG: [grpclb-<api.google.com>] Got an LB response: " + buildLbResponse(backends1))
        .inOrder();
    logs.clear();

    // Let subchannels be connected
    deliverSubchannelState(subchannel2, ConnectivityStateInfo.forNonError(READY));
    inOrder.verify(helper).updateBalancingState(eq(READY), pickerCaptor.capture());

    RoundRobinPicker picker1 = (RoundRobinPicker) pickerCaptor.getValue();

    assertThat(picker1.dropList).containsExactly(null, null);
    assertThat(picker1.pickList).containsExactly(
        new BackendEntry(subchannel2, getLoadRecorder(), "token0002"));

    deliverSubchannelState(subchannel1, ConnectivityStateInfo.forNonError(READY));
    inOrder.verify(helper).updateBalancingState(eq(READY), pickerCaptor.capture());

    RoundRobinPicker picker2 = (RoundRobinPicker) pickerCaptor.getValue();
    assertThat(picker2.dropList).containsExactly(null, null);
    assertThat(picker2.pickList).containsExactly(
        new BackendEntry(subchannel1, getLoadRecorder(), "token0001"),
        new BackendEntry(subchannel2, getLoadRecorder(), "token0002"))
        .inOrder();

    // Disconnected subchannels
    verify(subchannel1).requestConnection();
    deliverSubchannelState(subchannel1, ConnectivityStateInfo.forNonError(IDLE));
    verify(subchannel1, times(2)).requestConnection();
    inOrder.verify(helper).refreshNameResolution();
    inOrder.verify(helper).updateBalancingState(eq(READY), pickerCaptor.capture());

    RoundRobinPicker picker3 = (RoundRobinPicker) pickerCaptor.getValue();
    assertThat(picker3.dropList).containsExactly(null, null);
    assertThat(picker3.pickList).containsExactly(
        new BackendEntry(subchannel2, getLoadRecorder(), "token0002"));

    deliverSubchannelState(subchannel1, ConnectivityStateInfo.forNonError(CONNECTING));
    inOrder.verifyNoMoreInteractions();

    // As long as there is at least one READY subchannel, round robin will work.
    ConnectivityStateInfo errorState1 =
        ConnectivityStateInfo.forTransientFailure(Status.UNAVAILABLE.withDescription("error1"));
    deliverSubchannelState(subchannel1, errorState1);
    inOrder.verify(helper).refreshNameResolution();
    inOrder.verifyNoMoreInteractions();

    // If no subchannel is READY, some with error and the others are IDLE, will report CONNECTING
    verify(subchannel2).requestConnection();
    deliverSubchannelState(subchannel2, ConnectivityStateInfo.forNonError(IDLE));
    verify(subchannel2, times(2)).requestConnection();
    inOrder.verify(helper).updateBalancingState(eq(CONNECTING), pickerCaptor.capture());

    RoundRobinPicker picker4 = (RoundRobinPicker) pickerCaptor.getValue();
    assertThat(picker4.dropList).containsExactly(null, null);
    assertThat(picker4.pickList).containsExactly(BUFFER_ENTRY);

    // Update backends, with a drop entry
    List<ServerEntry> backends2 =
        Arrays.asList(
            new ServerEntry("127.0.0.1", 2030, "token0003"),  // New address
            new ServerEntry("token0003"),  // drop
            new ServerEntry("127.0.0.1", 2010, "token0004"),  // Existing address with token changed
            new ServerEntry("127.0.0.1", 2030, "token0005"),  // New address appearing second time
            new ServerEntry("token0006"));  // drop
    verify(subchannelPool, never())
        .returnSubchannel(same(subchannel1), any(ConnectivityStateInfo.class));

    lbResponseObserver.onNext(buildLbResponse(backends2));
    assertThat(logs).containsExactly(
        "DEBUG: [grpclb-<api.google.com>] Got an LB response: " + buildLbResponse(backends2))
        .inOrder();
    logs.clear();

    // not in backends2, closed
    verify(subchannelPool).returnSubchannel(same(subchannel1), same(errorState1));
    // backends2[2], will be kept
    verify(subchannelPool, never())
        .returnSubchannel(same(subchannel2), any(ConnectivityStateInfo.class));

    inOrder.verify(subchannelPool, never()).takeOrCreateSubchannel(
        eq(new EquivalentAddressGroup(backends2.get(2).addr, LB_BACKEND_ATTRS)),
        any(Attributes.class));
    inOrder.verify(subchannelPool).takeOrCreateSubchannel(
        eq(new EquivalentAddressGroup(backends2.get(0).addr, LB_BACKEND_ATTRS)),
        any(Attributes.class));

    ConnectivityStateInfo errorOnCachedSubchannel1 =
        ConnectivityStateInfo.forTransientFailure(
            Status.UNAVAILABLE.withDescription("You can get this error even if you are cached"));
    deliverSubchannelState(subchannel1, errorOnCachedSubchannel1);

    assertEquals(1, mockSubchannels.size());
    Subchannel subchannel3 = mockSubchannels.poll();
    verify(subchannel3).requestConnection();
    assertEquals(
        new EquivalentAddressGroup(backends2.get(0).addr, LB_BACKEND_ATTRS),
        subchannel3.getAddresses());
    inOrder.verify(helper).updateBalancingState(eq(CONNECTING), pickerCaptor.capture());
    RoundRobinPicker picker7 = (RoundRobinPicker) pickerCaptor.getValue();
    assertThat(picker7.dropList).containsExactly(
        null,
        new DropEntry(getLoadRecorder(), "token0003"),
        null,
        null,
        new DropEntry(getLoadRecorder(), "token0006")).inOrder();
    assertThat(picker7.pickList).containsExactly(BUFFER_ENTRY);

    // State updates on obsolete subchannel1 will only be passed to the pool
    deliverSubchannelState(subchannel1, ConnectivityStateInfo.forNonError(READY));
    deliverSubchannelState(
        subchannel1, ConnectivityStateInfo.forTransientFailure(Status.UNAVAILABLE));
    deliverSubchannelState(subchannel1, ConnectivityStateInfo.forNonError(SHUTDOWN));

    deliverSubchannelState(subchannel3, ConnectivityStateInfo.forNonError(READY));
    inOrder.verify(helper).updateBalancingState(eq(READY), pickerCaptor.capture());
    RoundRobinPicker picker8 = (RoundRobinPicker) pickerCaptor.getValue();
    assertThat(picker8.dropList).containsExactly(
        null,
        new DropEntry(getLoadRecorder(), "token0003"),
        null,
        null,
        new DropEntry(getLoadRecorder(), "token0006")).inOrder();
    // subchannel2 is still IDLE, thus not in the active list
    assertThat(picker8.pickList).containsExactly(
        new BackendEntry(subchannel3, getLoadRecorder(), "token0003"),
        new BackendEntry(subchannel3, getLoadRecorder(), "token0005")).inOrder();
    // subchannel2 becomes READY and makes it into the list
    deliverSubchannelState(subchannel2, ConnectivityStateInfo.forNonError(READY));
    inOrder.verify(helper).updateBalancingState(eq(READY), pickerCaptor.capture());
    RoundRobinPicker picker9 = (RoundRobinPicker) pickerCaptor.getValue();
    assertThat(picker9.dropList).containsExactly(
        null,
        new DropEntry(getLoadRecorder(), "token0003"),
        null,
        null,
        new DropEntry(getLoadRecorder(), "token0006")).inOrder();
    assertThat(picker9.pickList).containsExactly(
        new BackendEntry(subchannel3, getLoadRecorder(), "token0003"),
        new BackendEntry(subchannel2, getLoadRecorder(), "token0004"),
        new BackendEntry(subchannel3, getLoadRecorder(), "token0005")).inOrder();
    verify(subchannelPool, never())
        .returnSubchannel(same(subchannel3), any(ConnectivityStateInfo.class));

    // Update backends, with no entry
    lbResponseObserver.onNext(buildLbResponse(Collections.<ServerEntry>emptyList()));
    verify(subchannelPool)
        .returnSubchannel(same(subchannel2), eq(ConnectivityStateInfo.forNonError(READY)));
    verify(subchannelPool)
        .returnSubchannel(same(subchannel3), eq(ConnectivityStateInfo.forNonError(READY)));
    inOrder.verify(helper).updateBalancingState(eq(TRANSIENT_FAILURE), pickerCaptor.capture());
    RoundRobinPicker picker10 = (RoundRobinPicker) pickerCaptor.getValue();
    assertThat(picker10.dropList).isEmpty();
    assertThat(picker10.pickList)
        .containsExactly(new ErrorEntry(GrpclbState.NO_AVAILABLE_BACKENDS_STATUS));

    assertFalse(oobChannel.isShutdown());
    assertEquals(0, lbRequestObservers.size());
    verify(lbRequestObserver, never()).onCompleted();
    verify(lbRequestObserver, never()).onError(any(Throwable.class));

    // Load reporting was not requested, thus never scheduled
    assertEquals(0, fakeClock.numPendingTasks(LOAD_REPORTING_TASK_FILTER));

    verify(subchannelPool, never()).clear();
    balancer.shutdown();
    verify(subchannelPool).clear();
  }

  @Test
  public void roundRobinMode_subchannelStayTransientFailureUntilReady() {
    InOrder inOrder = inOrder(helper);
    List<EquivalentAddressGroup> grpclbBalancerList = createResolvedBalancerAddresses(1);
    deliverResolvedAddresses(Collections.<EquivalentAddressGroup>emptyList(), grpclbBalancerList);
    verify(mockLbService).balanceLoad(lbResponseObserverCaptor.capture());
    StreamObserver<LoadBalanceResponse> lbResponseObserver = lbResponseObserverCaptor.getValue();

    // Simulate receiving LB response
    List<ServerEntry> backends1 = Arrays.asList(
        new ServerEntry("127.0.0.1", 2000, "token0001"),
        new ServerEntry("127.0.0.1", 2010, "token0002"));
    lbResponseObserver.onNext(buildInitialResponse());
    lbResponseObserver.onNext(buildLbResponse(backends1));
    assertEquals(2, mockSubchannels.size());
    Subchannel subchannel1 = mockSubchannels.poll();
    Subchannel subchannel2 = mockSubchannels.poll();

    deliverSubchannelState(subchannel1, ConnectivityStateInfo.forNonError(CONNECTING));
    deliverSubchannelState(subchannel2, ConnectivityStateInfo.forNonError(CONNECTING));
    inOrder.verify(helper).updateBalancingState(eq(CONNECTING), any(SubchannelPicker.class));

    // Switch all subchannels to TRANSIENT_FAILURE, making the general state TRANSIENT_FAILURE too.
    Status error = Status.UNAVAILABLE.withDescription("error");
    deliverSubchannelState(subchannel1, ConnectivityStateInfo.forTransientFailure(error));
    inOrder.verify(helper).refreshNameResolution();
    deliverSubchannelState(subchannel2, ConnectivityStateInfo.forTransientFailure(error));
    inOrder.verify(helper).refreshNameResolution();
    inOrder.verify(helper).updateBalancingState(eq(TRANSIENT_FAILURE), pickerCaptor.capture());
    assertThat(((RoundRobinPicker) pickerCaptor.getValue()).pickList)
        .containsExactly(new ErrorEntry(error));

    // Switch subchannel1 to IDLE, then to CONNECTING, which are ignored since the previous
    // subchannel state is TRANSIENT_FAILURE. General state is unchanged.
    deliverSubchannelState(subchannel1, ConnectivityStateInfo.forNonError(IDLE));
    inOrder.verify(helper).refreshNameResolution();
    deliverSubchannelState(subchannel1, ConnectivityStateInfo.forNonError(CONNECTING));
    inOrder.verifyNoMoreInteractions();

    // Switch subchannel1 to READY, which will affect the general state
    deliverSubchannelState(subchannel1, ConnectivityStateInfo.forNonError(READY));
    inOrder.verify(helper).updateBalancingState(eq(READY), pickerCaptor.capture());
    assertThat(((RoundRobinPicker) pickerCaptor.getValue()).pickList)
        .containsExactly(new BackendEntry(subchannel1, getLoadRecorder(), "token0001"));
    inOrder.verifyNoMoreInteractions();
  }

  @Test
  public void grpclbFallback_initialTimeout_serverListReceivedBeforeTimerExpires() {
    subtestGrpclbFallbackTimeout(false, GrpclbState.FALLBACK_TIMEOUT_MS);
  }

  @Test
  public void grpclbFallback_initialTimeout_timerExpires() {
    subtestGrpclbFallbackTimeout(true, GrpclbState.FALLBACK_TIMEOUT_MS);
  }

  @Test
  public void grpclbFallback_timeout_serverListReceivedBeforeTimerExpires() {
    subtestGrpclbFallbackTimeout(false, 12345);
  }

  @Test
  public void grpclbFallback_timeout_timerExpires() {
    subtestGrpclbFallbackTimeout(true, 12345);
  }

  // Fallback or not within the period of the initial timeout.
  private void subtestGrpclbFallbackTimeout(boolean timerExpires, long timeout) {
    long loadReportIntervalMillis = 1983;
    InOrder inOrder = inOrder(helper, subchannelPool);

    // Create balancer and backend addresses
    List<EquivalentAddressGroup> backendList = createResolvedBackendAddresses(2);
    List<EquivalentAddressGroup> grpclbBalancerList = createResolvedBalancerAddresses(1);
    deliverResolvedAddresses(
        backendList, grpclbBalancerList, GrpclbConfig.create(Mode.ROUND_ROBIN, null, timeout));
    inOrder.verify(helper).createOobChannel(eq(xattr(grpclbBalancerList)),
        eq(lbAuthority(0) + NO_USE_AUTHORITY_SUFFIX));

    // Attempted to connect to balancer
    assertEquals(1, fakeOobChannels.size());
    ManagedChannel oobChannel = fakeOobChannels.poll();
    verify(mockLbService).balanceLoad(lbResponseObserverCaptor.capture());
    StreamObserver<LoadBalanceResponse> lbResponseObserver = lbResponseObserverCaptor.getValue();
    assertEquals(1, lbRequestObservers.size());
    StreamObserver<LoadBalanceRequest> lbRequestObserver = lbRequestObservers.poll();

    verify(lbRequestObserver).onNext(
        eq(LoadBalanceRequest.newBuilder().setInitialRequest(
            InitialLoadBalanceRequest.newBuilder().setName(SERVICE_AUTHORITY).build())
            .build()));
    lbResponseObserver.onNext(buildInitialResponse(loadReportIntervalMillis));
    // We don't care if these methods have been run.
    inOrder.verify(helper, atLeast(0)).getSynchronizationContext();
    inOrder.verify(helper, atLeast(0)).getScheduledExecutorService();

    inOrder.verifyNoMoreInteractions();

    assertEquals(1, fakeClock.numPendingTasks(FALLBACK_MODE_TASK_FILTER));
    fakeClock.forwardTime(timeout - 1, TimeUnit.MILLISECONDS);
    assertEquals(1, fakeClock.numPendingTasks(FALLBACK_MODE_TASK_FILTER));

    //////////////////////////////////
    // Fallback timer expires (or not)
    //////////////////////////////////
    if (timerExpires) {
      logs.clear();
      fakeClock.forwardTime(1, TimeUnit.MILLISECONDS);

      assertEquals(0, fakeClock.numPendingTasks(FALLBACK_MODE_TASK_FILTER));
      assertThat(logs)
          .containsExactly("INFO: [grpclb-<api.google.com>] Using fallback backends")
          .inOrder();

      // Fall back to the backends from resolver
      fallbackTestVerifyUseOfFallbackBackendLists(inOrder, backendList);

      assertFalse(oobChannel.isShutdown());
      verify(lbRequestObserver, never()).onCompleted();
    }

    //////////////////////////////////////////////////////////////////////
    // Name resolver sends new resolution results without any backend addr
    //////////////////////////////////////////////////////////////////////
    grpclbBalancerList = createResolvedBalancerAddresses(2);
    deliverResolvedAddresses(
        Collections.<EquivalentAddressGroup>emptyList(),
        grpclbBalancerList,
        GrpclbConfig.create(Mode.ROUND_ROBIN, null, timeout));

    // New addresses are updated to the OobChannel
    inOrder.verify(helper).updateOobChannelAddresses(
        same(oobChannel), eq(xattr(grpclbBalancerList)));

    if (timerExpires) {
      // Still in fallback logic, except that the backend list is empty
      for (Subchannel subchannel : mockSubchannels) {
        verify(subchannelPool).returnSubchannel(eq(subchannel), any(ConnectivityStateInfo.class));
      }

      // RPC error status includes message of balancer RPC timeout
      inOrder.verify(helper).updateBalancingState(eq(TRANSIENT_FAILURE), pickerCaptor.capture());
      PickResult result = pickerCaptor.getValue().pickSubchannel(mock(PickSubchannelArgs.class));
      assertThat(result.getStatus().getCode())
          .isEqualTo(Code.UNAVAILABLE);
      assertThat(result.getStatus().getDescription())
          .startsWith(GrpclbState.NO_FALLBACK_BACKENDS_STATUS.getDescription());
      assertThat(result.getStatus().getDescription())
          .contains(GrpclbState.BALANCER_TIMEOUT_STATUS.getDescription());
    }

    ////////////////////////////////////////////////////////////////
    // Name resolver sends new resolution results with backend addrs
    ////////////////////////////////////////////////////////////////
    // prevents the cached subchannel to be used
    subchannelPool.clear();
    backendList = createResolvedBackendAddresses(2);
    grpclbBalancerList = createResolvedBalancerAddresses(1);
    deliverResolvedAddresses(
        backendList, grpclbBalancerList, GrpclbConfig.create(Mode.ROUND_ROBIN, null, timeout));

    // New LB address is updated to the OobChannel
    inOrder.verify(helper).updateOobChannelAddresses(
        same(oobChannel), eq(xattr(grpclbBalancerList)));

    if (timerExpires) {
      // New backend addresses are used for fallback
      fallbackTestVerifyUseOfFallbackBackendLists(
          inOrder, Arrays.asList(backendList.get(0), backendList.get(1)));
    }

    ////////////////////////////////////////////////
    // Break the LB stream after the timer expires
    ////////////////////////////////////////////////
    if (timerExpires) {
      Status streamError = Status.UNAVAILABLE.withDescription("OOB stream broken");
      lbResponseObserver.onError(streamError.asException());

      // The error will NOT propagate to picker because fallback list is in use.
      inOrder.verify(helper, never())
          .updateBalancingState(any(ConnectivityState.class), any(SubchannelPicker.class));
      // A new stream is created
      verify(mockLbService, times(2)).balanceLoad(lbResponseObserverCaptor.capture());
      lbResponseObserver = lbResponseObserverCaptor.getValue();
      assertEquals(1, lbRequestObservers.size());
      lbRequestObserver = lbRequestObservers.poll();
      verify(lbRequestObserver).onNext(
          eq(LoadBalanceRequest.newBuilder().setInitialRequest(
              InitialLoadBalanceRequest.newBuilder().setName(SERVICE_AUTHORITY).build())
              .build()));
    }

    /////////////////////////////////
    // Balancer returns a server list
    /////////////////////////////////
    List<ServerEntry> serverList = Arrays.asList(
        new ServerEntry("127.0.0.1", 2000, "token0001"),
        new ServerEntry("127.0.0.1", 2010, "token0002"));
    lbResponseObserver.onNext(buildInitialResponse());
    lbResponseObserver.onNext(buildLbResponse(serverList));

    // Balancer-provided server list now in effect
    fallbackTestVerifyUseOfBalancerBackendLists(inOrder, serverList);

    ///////////////////////////////////////////////////////////////
    // New backend addresses from resolver outside of fallback mode
    ///////////////////////////////////////////////////////////////
    backendList = createResolvedBackendAddresses(1);
    grpclbBalancerList = createResolvedBalancerAddresses(1);
    deliverResolvedAddresses(
        backendList, grpclbBalancerList, GrpclbConfig.create(Mode.ROUND_ROBIN, null, timeout));
    // Will not affect the round robin list at all
    inOrder.verify(helper, never())
        .updateBalancingState(any(ConnectivityState.class), any(SubchannelPicker.class));

    // No fallback timeout timer scheduled.
    assertEquals(0, fakeClock.numPendingTasks(FALLBACK_MODE_TASK_FILTER));
  }

  @Test
  public void grpclbFallback_breakLbStreamBeforeFallbackTimerExpires() {
    long loadReportIntervalMillis = 1983;
    InOrder inOrder = inOrder(helper, subchannelPool);

    // Create balancer and backend addresses
    List<EquivalentAddressGroup> backendList = createResolvedBackendAddresses(2);
    List<EquivalentAddressGroup> grpclbBalancerList = createResolvedBalancerAddresses(1);
    deliverResolvedAddresses(backendList, grpclbBalancerList);

    inOrder.verify(helper).createOobChannel(eq(xattr(grpclbBalancerList)),
        eq(lbAuthority(0) + NO_USE_AUTHORITY_SUFFIX));

    // Attempted to connect to balancer
    assertThat(fakeOobChannels).hasSize(1);
    verify(mockLbService).balanceLoad(lbResponseObserverCaptor.capture());
    StreamObserver<LoadBalanceResponse> lbResponseObserver = lbResponseObserverCaptor.getValue();
    assertThat(lbRequestObservers).hasSize(1);
    StreamObserver<LoadBalanceRequest> lbRequestObserver = lbRequestObservers.poll();

    verify(lbRequestObserver).onNext(
        eq(LoadBalanceRequest.newBuilder().setInitialRequest(
            InitialLoadBalanceRequest.newBuilder().setName(SERVICE_AUTHORITY).build())
            .build()));
    lbResponseObserver.onNext(buildInitialResponse(loadReportIntervalMillis));
    // We don't care if these methods have been run.
    inOrder.verify(helper, atLeast(0)).getSynchronizationContext();
    inOrder.verify(helper, atLeast(0)).getScheduledExecutorService();

    inOrder.verifyNoMoreInteractions();

    assertEquals(1, fakeClock.numPendingTasks(FALLBACK_MODE_TASK_FILTER));

    /////////////////////////////////////////////
    // Break the LB stream before timer expires
    /////////////////////////////////////////////
    Status streamError = Status.UNAVAILABLE.withDescription("OOB stream broken");
    lbResponseObserver.onError(streamError.asException());

    // Fallback time has been short-circuited
    assertEquals(0, fakeClock.numPendingTasks(FALLBACK_MODE_TASK_FILTER));

    // Fall back to the backends from resolver
    fallbackTestVerifyUseOfFallbackBackendLists(
        inOrder, Arrays.asList(backendList.get(0), backendList.get(1)));

    // A new stream is created
    verify(mockLbService, times(2)).balanceLoad(lbResponseObserverCaptor.capture());
    assertThat(lbRequestObservers).hasSize(1);
    lbRequestObserver = lbRequestObservers.poll();
    verify(lbRequestObserver).onNext(
        eq(LoadBalanceRequest.newBuilder().setInitialRequest(
            InitialLoadBalanceRequest.newBuilder().setName(SERVICE_AUTHORITY).build())
            .build()));

    //////////////////////////////////////////////////////////////////////
    // Name resolver sends new resolution results without any backend addr
    //////////////////////////////////////////////////////////////////////
    deliverResolvedAddresses(Collections.<EquivalentAddressGroup>emptyList(), grpclbBalancerList);

    // Still in fallback logic, except that the backend list is empty
    for (Subchannel subchannel : mockSubchannels) {
      verify(subchannelPool).returnSubchannel(eq(subchannel), any(ConnectivityStateInfo.class));
    }

    // RPC error status includes error of balancer stream
    inOrder.verify(helper).updateBalancingState(eq(TRANSIENT_FAILURE), pickerCaptor.capture());
    PickResult result = pickerCaptor.getValue().pickSubchannel(mock(PickSubchannelArgs.class));
    assertThat(result.getStatus().getCode()).isEqualTo(Code.UNAVAILABLE);
    assertThat(result.getStatus().getDescription())
        .startsWith(GrpclbState.NO_FALLBACK_BACKENDS_STATUS.getDescription());
    assertThat(result.getStatus().getDescription()).contains(streamError.getDescription());
  }

  @Test
  public void grpclbFallback_noBalancerAddress() {
    InOrder inOrder = inOrder(helper, subchannelPool);

    // Create 5 distinct backends
    List<EquivalentAddressGroup> backends = createResolvedBackendAddresses(5);

    // Name resolver gives the first two backend addresses
    List<EquivalentAddressGroup> backendList = backends.subList(0, 2);
    deliverResolvedAddresses(backendList, Collections.<EquivalentAddressGroup>emptyList());

    assertThat(logs).contains("INFO: [grpclb-<api.google.com>] Using fallback backends");

    // Fall back to the backends from resolver
    fallbackTestVerifyUseOfFallbackBackendLists(inOrder, backendList);

    // No fallback timeout timer scheduled.
    assertEquals(0, fakeClock.numPendingTasks(FALLBACK_MODE_TASK_FILTER));
    verify(helper, never())
        .createOobChannel(ArgumentMatchers.<EquivalentAddressGroup>anyList(), anyString());
    logs.clear();

    /////////////////////////////////////////////////////////////////////////////////////////
    // Name resolver sends new resolution results with new backend addr but no balancer addr
    /////////////////////////////////////////////////////////////////////////////////////////
    // Name resolver then gives the last three backends
    backendList = backends.subList(2, 5);
    deliverResolvedAddresses(backendList, Collections.<EquivalentAddressGroup>emptyList());

    assertThat(logs).contains("INFO: [grpclb-<api.google.com>] Using fallback backends");

    // Shift to use updated backends
    fallbackTestVerifyUseOfFallbackBackendLists(inOrder, backendList);
    logs.clear();

    ///////////////////////////////////////////////////////////////////////////////////////
    // Name resolver sends new resolution results without any backend addr or balancer addr
    ///////////////////////////////////////////////////////////////////////////////////////
    deliverResolvedAddresses(Collections.<EquivalentAddressGroup>emptyList(),
        Collections.<EquivalentAddressGroup>emptyList());
    assertThat(logs).containsExactly(
        "DEBUG: [grpclb-<api.google.com>] Error: Status{code=UNAVAILABLE, "
            + "description=No backend or balancer addresses found, cause=null}");

    // Keep using existing fallback addresses without interruption
    for (Subchannel subchannel : mockSubchannels) {
      verify(subchannelPool, never())
          .returnSubchannel(eq(subchannel), any(ConnectivityStateInfo.class));
    }
    verify(helper, never())
        .updateBalancingState(eq(TRANSIENT_FAILURE), any(SubchannelPicker.class));
  }

  /**
   * A test for a situation where we first only get backend addresses resolved and then in a
   * later name resolution get both backend and load balancer addresses. The first instance
   * will switch us to using fallback backends and it is important that in the second instance
   * we do not start a fallback timer as it will fail when it triggers if the fallback backends
   * are already in use.
   */
  @Test
  public void grpclbFallback_noTimerWhenAlreadyInFallback() {
    // Initially we only get backend addresses without any LB ones. This should get us to use
    // fallback backends from the start as we won't be able to even talk to the load balancer.
    // No fallback timer would be started as we already started to use fallback backends.
    deliverResolvedAddresses(createResolvedBalancerAddresses(1),
        Collections.<EquivalentAddressGroup>emptyList());
    assertEquals(0, fakeClock.numPendingTasks(FALLBACK_MODE_TASK_FILTER));

    // Later a new name resolution call happens and we get both backend and LB addresses. Since we
    // are already operating with fallback backends a fallback timer should not be started to move
    // us to fallback mode.
    deliverResolvedAddresses(Collections.<EquivalentAddressGroup>emptyList(),
        createResolvedBalancerAddresses(1));

    // If a fallback timer is started it will eventually throw an exception when it tries to switch
    // us to using fallback backends when we already are using them.
    assertEquals(0, fakeClock.numPendingTasks(FALLBACK_MODE_TASK_FILTER));
  }

  @Test
  public void grpclbFallback_balancerLost() {
    subtestGrpclbFallbackConnectionLost(true, false);
  }

  @Test
  public void grpclbFallback_subchannelsLost() {
    subtestGrpclbFallbackConnectionLost(false, true);
  }

  @Test
  public void grpclbFallback_allLost() {
    subtestGrpclbFallbackConnectionLost(true, true);
  }

  // Fallback outside of the initial timeout, where all connections are lost.
  private void subtestGrpclbFallbackConnectionLost(
      boolean balancerBroken, boolean allSubchannelsBroken) {
    long loadReportIntervalMillis = 1983;
    InOrder inOrder = inOrder(helper, mockLbService, subchannelPool);

    // Create balancer and backend addresses
    List<EquivalentAddressGroup> backendList = createResolvedBackendAddresses(2);
    List<EquivalentAddressGroup> grpclbBalancerList = createResolvedBalancerAddresses(1);
    deliverResolvedAddresses(backendList, grpclbBalancerList);

    inOrder.verify(helper).createOobChannel(eq(xattr(grpclbBalancerList)),
        eq(lbAuthority(0) + NO_USE_AUTHORITY_SUFFIX));

    // Attempted to connect to balancer
    assertEquals(1, fakeOobChannels.size());
    fakeOobChannels.poll();
    inOrder.verify(mockLbService).balanceLoad(lbResponseObserverCaptor.capture());
    StreamObserver<LoadBalanceResponse> lbResponseObserver = lbResponseObserverCaptor.getValue();
    assertEquals(1, lbRequestObservers.size());
    StreamObserver<LoadBalanceRequest> lbRequestObserver = lbRequestObservers.poll();

    verify(lbRequestObserver).onNext(
        eq(LoadBalanceRequest.newBuilder().setInitialRequest(
            InitialLoadBalanceRequest.newBuilder().setName(SERVICE_AUTHORITY).build())
            .build()));
    lbResponseObserver.onNext(buildInitialResponse(loadReportIntervalMillis));
    // We don't care if these methods have been run.
    inOrder.verify(helper, atLeast(0)).getSynchronizationContext();
    inOrder.verify(helper, atLeast(0)).getScheduledExecutorService();

    inOrder.verifyNoMoreInteractions();

    // Balancer returns a server list
    List<ServerEntry> serverList = Arrays.asList(
        new ServerEntry("127.0.0.1", 2000, "token0001"),
        new ServerEntry("127.0.0.1", 2010, "token0002"));
    lbResponseObserver.onNext(buildInitialResponse());
    lbResponseObserver.onNext(buildLbResponse(serverList));

    List<Subchannel> subchannels = fallbackTestVerifyUseOfBalancerBackendLists(inOrder, serverList);

    // Break connections
    if (balancerBroken) {
      lbResponseObserver.onError(Status.UNAVAILABLE.asException());
      // A new stream to LB is created
      inOrder.verify(mockLbService).balanceLoad(lbResponseObserverCaptor.capture());
      lbResponseObserver = lbResponseObserverCaptor.getValue();
      assertEquals(1, lbRequestObservers.size());
      lbRequestObserver = lbRequestObservers.poll();
      inOrder.verify(helper).refreshNameResolution();
    }
    if (allSubchannelsBroken) {
      for (Subchannel subchannel : subchannels) {
        // A READY subchannel transits to IDLE when receiving a go-away
        deliverSubchannelState(subchannel, ConnectivityStateInfo.forNonError(IDLE));
        inOrder.verify(helper).refreshNameResolution();
      }
    }

    if (balancerBroken && allSubchannelsBroken) {
      // Going into fallback
      subchannels = fallbackTestVerifyUseOfFallbackBackendLists(
          inOrder, Arrays.asList(backendList.get(0), backendList.get(1)));

      // When in fallback mode, fallback timer should not be scheduled when all backend
      // connections are lost
      for (Subchannel subchannel : subchannels) {
        deliverSubchannelState(subchannel, ConnectivityStateInfo.forNonError(IDLE));
        inOrder.verify(helper).refreshNameResolution();
      }

      // Exit fallback mode or cancel fallback timer when receiving a new server list from balancer
      List<ServerEntry> serverList2 = Arrays.asList(
          new ServerEntry("127.0.0.1", 2001, "token0003"),
          new ServerEntry("127.0.0.1", 2011, "token0004"));
      lbResponseObserver.onNext(buildInitialResponse());
      lbResponseObserver.onNext(buildLbResponse(serverList2));

      fallbackTestVerifyUseOfBalancerBackendLists(inOrder, serverList2);
    }
    assertEquals(0, fakeClock.numPendingTasks(FALLBACK_MODE_TASK_FILTER));

    // No subchannel to fallback backends should have been created if no fallback happened
    if (!(balancerBroken && allSubchannelsBroken)) {
      verify(subchannelPool, never()).takeOrCreateSubchannel(
          eq(backendList.get(0)), any(Attributes.class));
      verify(subchannelPool, never()).takeOrCreateSubchannel(
          eq(backendList.get(1)), any(Attributes.class));
    }
  }

  @Test
  public void grpclbFallback_allLost_failToFallback() {
    long loadReportIntervalMillis = 1983;
    InOrder inOrder = inOrder(helper, mockLbService, subchannelPool);

    // Create balancer and (empty) backend addresses
    List<EquivalentAddressGroup> grpclbBalancerList = createResolvedBalancerAddresses(1);
    deliverResolvedAddresses(Collections.<EquivalentAddressGroup>emptyList(), grpclbBalancerList);

    inOrder.verify(helper).createOobChannel(eq(xattr(grpclbBalancerList)),
        eq(lbAuthority(0) + NO_USE_AUTHORITY_SUFFIX));

    // Attempted to connect to balancer
    assertEquals(1, fakeOobChannels.size());
    fakeOobChannels.poll();
    inOrder.verify(mockLbService).balanceLoad(lbResponseObserverCaptor.capture());
    StreamObserver<LoadBalanceResponse> lbResponseObserver = lbResponseObserverCaptor.getValue();
    assertEquals(1, lbRequestObservers.size());
    StreamObserver<LoadBalanceRequest> lbRequestObserver = lbRequestObservers.poll();

    verify(lbRequestObserver).onNext(
        eq(LoadBalanceRequest.newBuilder().setInitialRequest(
            InitialLoadBalanceRequest.newBuilder().setName(SERVICE_AUTHORITY).build())
            .build()));
    lbResponseObserver.onNext(buildInitialResponse(loadReportIntervalMillis));
    // We don't care if these methods have been run.
    inOrder.verify(helper, atLeast(0)).getSynchronizationContext();
    inOrder.verify(helper, atLeast(0)).getScheduledExecutorService();

    inOrder.verifyNoMoreInteractions();

    // Balancer returns a server list
    List<ServerEntry> serverList = Arrays.asList(
        new ServerEntry("127.0.0.1", 2000, "token0001"),
        new ServerEntry("127.0.0.1", 2010, "token0002"));
    lbResponseObserver.onNext(buildInitialResponse());
    lbResponseObserver.onNext(buildLbResponse(serverList));

    List<Subchannel> subchannels = fallbackTestVerifyUseOfBalancerBackendLists(inOrder, serverList);

    // Break connections
    lbResponseObserver.onError(Status.UNAVAILABLE.asException());
    // A new stream to LB is created
    inOrder.verify(mockLbService).balanceLoad(lbResponseObserverCaptor.capture());
    assertEquals(1, lbRequestObservers.size());

    // Break all subchannel connections
    Status error = Status.UNAUTHENTICATED.withDescription("Permission denied");
    for (Subchannel subchannel : subchannels) {
      deliverSubchannelState(subchannel, ConnectivityStateInfo.forTransientFailure(error));
    }

    // Recycle all subchannels
    for (Subchannel subchannel : subchannels) {
      verify(subchannelPool).returnSubchannel(eq(subchannel), any(ConnectivityStateInfo.class));
    }

    // RPC error status includes errors of subchannels to balancer-provided backends
    inOrder.verify(helper).updateBalancingState(eq(TRANSIENT_FAILURE), pickerCaptor.capture());
    PickResult result = pickerCaptor.getValue().pickSubchannel(mock(PickSubchannelArgs.class));
    assertThat(result.getStatus().getCode()).isEqualTo(Code.UNAVAILABLE);
    assertThat(result.getStatus().getDescription())
        .startsWith(GrpclbState.NO_FALLBACK_BACKENDS_STATUS.getDescription());
    assertThat(result.getStatus().getDescription()).contains(error.getDescription());
  }

  private List<Subchannel> fallbackTestVerifyUseOfFallbackBackendLists(
      InOrder inOrder, List<EquivalentAddressGroup> addrs) {
    return fallbackTestVerifyUseOfBackendLists(inOrder, addrs, null);
  }

  private List<Subchannel> fallbackTestVerifyUseOfBalancerBackendLists(
      InOrder inOrder, List<ServerEntry> servers) {
    ArrayList<EquivalentAddressGroup> addrs = new ArrayList<>();
    ArrayList<String> tokens = new ArrayList<>();
    for (ServerEntry server : servers) {
      addrs.add(new EquivalentAddressGroup(server.addr, LB_BACKEND_ATTRS));
      tokens.add(server.token);
    }
    return fallbackTestVerifyUseOfBackendLists(inOrder, addrs, tokens);
  }

  private List<Subchannel> fallbackTestVerifyUseOfBackendLists(
      InOrder inOrder, List<EquivalentAddressGroup> addrs,
      @Nullable List<String> tokens) {
    if (tokens != null) {
      assertEquals(addrs.size(), tokens.size());
    }
    for (EquivalentAddressGroup addr : addrs) {
      inOrder.verify(subchannelPool).takeOrCreateSubchannel(eq(addr), any(Attributes.class));
    }
    RoundRobinPicker picker = (RoundRobinPicker) currentPicker;
    assertThat(picker.dropList).containsExactlyElementsIn(Collections.nCopies(addrs.size(), null));
    assertThat(picker.pickList).containsExactly(GrpclbState.BUFFER_ENTRY);
    assertEquals(addrs.size(), mockSubchannels.size());
    ArrayList<Subchannel> subchannels = new ArrayList<>(mockSubchannels);
    mockSubchannels.clear();
    for (Subchannel subchannel : subchannels) {
      deliverSubchannelState(subchannel, ConnectivityStateInfo.forNonError(CONNECTING));
    }
    inOrder.verify(helper, atLeast(0))
        .updateBalancingState(eq(CONNECTING), any(SubchannelPicker.class));
    inOrder.verify(helper, never())
        .updateBalancingState(any(ConnectivityState.class), any(SubchannelPicker.class));

    ArrayList<BackendEntry> pickList = new ArrayList<>();
    for (int i = 0; i < addrs.size(); i++) {
      Subchannel subchannel = subchannels.get(i);
      BackendEntry backend;
      if (tokens == null) {
        backend = new BackendEntry(subchannel);
      } else {
        backend = new BackendEntry(subchannel, getLoadRecorder(), tokens.get(i));
      }
      pickList.add(backend);
      deliverSubchannelState(subchannel, ConnectivityStateInfo.forNonError(READY));
      inOrder.verify(helper).updateBalancingState(eq(READY), pickerCaptor.capture());
      picker = (RoundRobinPicker) pickerCaptor.getValue();
      assertThat(picker.dropList)
          .containsExactlyElementsIn(Collections.nCopies(addrs.size(), null));
      assertThat(picker.pickList).containsExactlyElementsIn(pickList);
      inOrder.verify(helper, never())
          .updateBalancingState(any(ConnectivityState.class), any(SubchannelPicker.class));
    }
    return subchannels;
  }

  @Test
  public void grpclbMultipleAuthorities() throws Exception {
    List<EquivalentAddressGroup> backendList = Collections.singletonList(
        new EquivalentAddressGroup(new FakeSocketAddress("not-a-lb-address")));
    List<EquivalentAddressGroup> grpclbBalancerList = Arrays.asList(
        new EquivalentAddressGroup(
            new FakeSocketAddress("fake-address-1"),
            lbAttributes("fake-authority-1")),
        new EquivalentAddressGroup(
            new FakeSocketAddress("fake-address-2"),
            lbAttributes("fake-authority-2")),
        new EquivalentAddressGroup(
            new FakeSocketAddress("fake-address-3"),
            lbAttributes("fake-authority-1").toBuilder()
                .set(GrpclbConstants.TOKEN_ATTRIBUTE_KEY, "value").build()
            ));
    deliverResolvedAddresses(backendList, grpclbBalancerList);

    List<EquivalentAddressGroup> goldenOobEagList =
        Arrays.asList(
            new EquivalentAddressGroup(
                new FakeSocketAddress("fake-address-1"),
                Attributes.newBuilder()
                    .set(GrpclbConstants.ATTR_LB_ADDR_AUTHORITY, "fake-authority-1")
                    .set(EquivalentAddressGroup.ATTR_AUTHORITY_OVERRIDE, "fake-authority-1")
                    .build()),
            new EquivalentAddressGroup(
                new FakeSocketAddress("fake-address-2"),
                Attributes.newBuilder()
                    .set(GrpclbConstants.ATTR_LB_ADDR_AUTHORITY, "fake-authority-2")
                    .set(EquivalentAddressGroup.ATTR_AUTHORITY_OVERRIDE, "fake-authority-2")
                    .build()),
            new EquivalentAddressGroup(
                new FakeSocketAddress("fake-address-3"),
                Attributes.newBuilder()
                    .set(GrpclbConstants.ATTR_LB_ADDR_AUTHORITY, "fake-authority-1")
                    .set(GrpclbConstants.TOKEN_ATTRIBUTE_KEY, "value")
                    .set(EquivalentAddressGroup.ATTR_AUTHORITY_OVERRIDE, "fake-authority-1")
                    .build()
            ));

    verify(helper).createOobChannel(eq(goldenOobEagList),
        eq("fake-authority-1" + NO_USE_AUTHORITY_SUFFIX));
  }

  @Test
  public void grpclbBalancerStreamClosedAndRetried() throws Exception {
    LoadBalanceRequest expectedInitialRequest =
        LoadBalanceRequest.newBuilder()
            .setInitialRequest(
                InitialLoadBalanceRequest.newBuilder().setName(SERVICE_AUTHORITY).build())
            .build();
    InOrder inOrder =
        inOrder(mockLbService, backoffPolicyProvider, backoffPolicy1, backoffPolicy2, helper);
    List<EquivalentAddressGroup> grpclbBalancerList = createResolvedBalancerAddresses(1);
    deliverResolvedAddresses(Collections.<EquivalentAddressGroup>emptyList(), grpclbBalancerList);

    assertEquals(1, fakeOobChannels.size());
    @SuppressWarnings("unused")
    ManagedChannel oobChannel = fakeOobChannels.poll();

    // First balancer RPC
    inOrder.verify(mockLbService).balanceLoad(lbResponseObserverCaptor.capture());
    StreamObserver<LoadBalanceResponse> lbResponseObserver = lbResponseObserverCaptor.getValue();
    assertEquals(1, lbRequestObservers.size());
    StreamObserver<LoadBalanceRequest> lbRequestObserver = lbRequestObservers.poll();
    verify(lbRequestObserver).onNext(eq(expectedInitialRequest));
    assertEquals(0, fakeClock.numPendingTasks(LB_RPC_RETRY_TASK_FILTER));

    // Balancer closes it immediately (erroneously)
    lbResponseObserver.onCompleted();

    // Will start backoff sequence 1 (10ns)
    inOrder.verify(backoffPolicyProvider).get();
    inOrder.verify(backoffPolicy1).nextBackoffNanos();
    assertEquals(1, fakeClock.numPendingTasks(LB_RPC_RETRY_TASK_FILTER));
    inOrder.verify(helper).refreshNameResolution();

    // Fast-forward to a moment before the retry
    fakeClock.forwardNanos(9);
    verifyNoMoreInteractions(mockLbService);
    // Then time for retry
    fakeClock.forwardNanos(1);
    inOrder.verify(mockLbService).balanceLoad(lbResponseObserverCaptor.capture());
    lbResponseObserver = lbResponseObserverCaptor.getValue();
    assertEquals(1, lbRequestObservers.size());
    lbRequestObserver = lbRequestObservers.poll();
    verify(lbRequestObserver).onNext(eq(expectedInitialRequest));
    assertEquals(0, fakeClock.numPendingTasks(LB_RPC_RETRY_TASK_FILTER));

    // Balancer closes it with an error.
    lbResponseObserver.onError(Status.UNAVAILABLE.asException());
    // Will continue the backoff sequence 1 (100ns)
    verifyNoMoreInteractions(backoffPolicyProvider);
    inOrder.verify(backoffPolicy1).nextBackoffNanos();
    assertEquals(1, fakeClock.numPendingTasks(LB_RPC_RETRY_TASK_FILTER));
    inOrder.verify(helper).refreshNameResolution();

    // Fast-forward to a moment before the retry
    fakeClock.forwardNanos(100 - 1);
    verifyNoMoreInteractions(mockLbService);
    // Then time for retry
    fakeClock.forwardNanos(1);
    inOrder.verify(mockLbService).balanceLoad(lbResponseObserverCaptor.capture());
    lbResponseObserver = lbResponseObserverCaptor.getValue();
    assertEquals(1, lbRequestObservers.size());
    lbRequestObserver = lbRequestObservers.poll();
    verify(lbRequestObserver).onNext(eq(expectedInitialRequest));
    assertEquals(0, fakeClock.numPendingTasks(LB_RPC_RETRY_TASK_FILTER));

    // Balancer sends initial response.
    lbResponseObserver.onNext(buildInitialResponse());

    // Then breaks the RPC
    lbResponseObserver.onError(Status.UNAVAILABLE.asException());

    // Will reset the retry sequence and retry immediately, because balancer has responded.
    inOrder.verify(backoffPolicyProvider).get();
    inOrder.verify(mockLbService).balanceLoad(lbResponseObserverCaptor.capture());
    lbResponseObserver = lbResponseObserverCaptor.getValue();
    assertEquals(1, lbRequestObservers.size());
    lbRequestObserver = lbRequestObservers.poll();
    verify(lbRequestObserver).onNext(eq(expectedInitialRequest));
    inOrder.verify(helper).refreshNameResolution();

    // Fail the retry after spending 4ns
    fakeClock.forwardNanos(4);
    lbResponseObserver.onError(Status.UNAVAILABLE.asException());

    // Will be on the first retry (10ns) of backoff sequence 2.
    inOrder.verify(backoffPolicy2).nextBackoffNanos();
    assertEquals(1, fakeClock.numPendingTasks(LB_RPC_RETRY_TASK_FILTER));
    inOrder.verify(helper).refreshNameResolution();

    // Fast-forward to a moment before the retry, the time spent in the last try is deducted.
    fakeClock.forwardNanos(10 - 4 - 1);
    verifyNoMoreInteractions(mockLbService);
    // Then time for retry
    fakeClock.forwardNanos(1);
    inOrder.verify(mockLbService).balanceLoad(lbResponseObserverCaptor.capture());
    assertEquals(1, lbRequestObservers.size());
    lbRequestObserver = lbRequestObservers.poll();
    verify(lbRequestObserver).onNext(eq(expectedInitialRequest));
    assertEquals(0, fakeClock.numPendingTasks(LB_RPC_RETRY_TASK_FILTER));

    // Wrapping up
    verify(backoffPolicyProvider, times(2)).get();
    verify(backoffPolicy1, times(2)).nextBackoffNanos();
    verify(backoffPolicy2, times(1)).nextBackoffNanos();
    verify(helper, times(4)).refreshNameResolution();
  }

  @Test
  public void grpclbWorking_pickFirstMode() throws Exception {
    InOrder inOrder = inOrder(helper);

    List<EquivalentAddressGroup> grpclbBalancerList = createResolvedBalancerAddresses(1);

    deliverResolvedAddresses(
        Collections.<EquivalentAddressGroup>emptyList(),
        grpclbBalancerList,
        GrpclbConfig.create(Mode.PICK_FIRST));

    assertEquals(1, fakeOobChannels.size());
    verify(mockLbService).balanceLoad(lbResponseObserverCaptor.capture());
    StreamObserver<LoadBalanceResponse> lbResponseObserver = lbResponseObserverCaptor.getValue();
    assertEquals(1, lbRequestObservers.size());
    StreamObserver<LoadBalanceRequest> lbRequestObserver = lbRequestObservers.poll();
    verify(lbRequestObserver).onNext(
        eq(LoadBalanceRequest.newBuilder().setInitialRequest(
            InitialLoadBalanceRequest.newBuilder().setName(SERVICE_AUTHORITY).build())
            .build()));

    // Simulate receiving LB response
    List<ServerEntry> backends1 = Arrays.asList(
        new ServerEntry("127.0.0.1", 2000, "token0001"),
        new ServerEntry("127.0.0.1", 2010, "token0002"));
    inOrder.verify(helper, never())
        .updateBalancingState(any(ConnectivityState.class), any(SubchannelPicker.class));
    lbResponseObserver.onNext(buildInitialResponse());
    lbResponseObserver.onNext(buildLbResponse(backends1));

    inOrder.verify(helper).createSubchannel(createSubchannelArgsCaptor.capture());
    CreateSubchannelArgs createSubchannelArgs = createSubchannelArgsCaptor.getValue();
    assertThat(createSubchannelArgs.getAddresses())
        .containsExactly(
            new EquivalentAddressGroup(backends1.get(0).addr, eagAttrsWithToken("token0001")),
            new EquivalentAddressGroup(backends1.get(1).addr, eagAttrsWithToken("token0002")));

    // Initially IDLE
    inOrder.verify(helper).updateBalancingState(eq(IDLE), pickerCaptor.capture());
    RoundRobinPicker picker0 = (RoundRobinPicker) pickerCaptor.getValue();

    // Only one subchannel is created
    assertThat(mockSubchannels).hasSize(1);
    Subchannel subchannel = mockSubchannels.poll();
    assertThat(picker0.dropList).containsExactly(null, null);
    assertThat(picker0.pickList).containsExactly(new IdleSubchannelEntry(subchannel, syncContext));

    // PICK_FIRST doesn't eagerly connect
    verify(subchannel, never()).requestConnection();

    // CONNECTING
    deliverSubchannelState(subchannel, ConnectivityStateInfo.forNonError(CONNECTING));
    inOrder.verify(helper).updateBalancingState(eq(CONNECTING), pickerCaptor.capture());
    RoundRobinPicker picker1 = (RoundRobinPicker) pickerCaptor.getValue();
    assertThat(picker1.dropList).containsExactly(null, null);
    assertThat(picker1.pickList).containsExactly(BUFFER_ENTRY);

    // TRANSIENT_FAILURE
    Status error = Status.UNAVAILABLE.withDescription("Simulated connection error");
    deliverSubchannelState(subchannel, ConnectivityStateInfo.forTransientFailure(error));
    inOrder.verify(helper).updateBalancingState(eq(TRANSIENT_FAILURE), pickerCaptor.capture());
    RoundRobinPicker picker2 = (RoundRobinPicker) pickerCaptor.getValue();
    assertThat(picker2.dropList).containsExactly(null, null);
    assertThat(picker2.pickList).containsExactly(new ErrorEntry(error));

    // READY
    deliverSubchannelState(subchannel, ConnectivityStateInfo.forNonError(READY));
    inOrder.verify(helper).updateBalancingState(eq(READY), pickerCaptor.capture());
    RoundRobinPicker picker3 = (RoundRobinPicker) pickerCaptor.getValue();
    assertThat(picker3.dropList).containsExactly(null, null);
    assertThat(picker3.pickList).containsExactly(
        new BackendEntry(subchannel, new TokenAttachingTracerFactory(getLoadRecorder())));


    // New server list with drops
    List<ServerEntry> backends2 = Arrays.asList(
        new ServerEntry("127.0.0.1", 2000, "token0001"),
        new ServerEntry("token0003"),  // drop
        new ServerEntry("127.0.0.1", 2020, "token0004"));
    inOrder.verify(helper, never())
        .updateBalancingState(any(ConnectivityState.class), any(SubchannelPicker.class));
    lbResponseObserver.onNext(buildLbResponse(backends2));

    // new addresses will be updated to the existing subchannel
    // createSubchannel() has ever been called only once
    verify(helper, times(1)).createSubchannel(any(CreateSubchannelArgs.class));
    assertThat(mockSubchannels).isEmpty();
    verify(subchannel).updateAddresses(
        eq(Arrays.asList(
            new EquivalentAddressGroup(backends2.get(0).addr, eagAttrsWithToken("token0001")),
            new EquivalentAddressGroup(backends2.get(2).addr,
                eagAttrsWithToken("token0004")))));
    inOrder.verify(helper).updateBalancingState(eq(READY), pickerCaptor.capture());
    RoundRobinPicker picker4 = (RoundRobinPicker) pickerCaptor.getValue();
    assertThat(picker4.dropList).containsExactly(
        null, new DropEntry(getLoadRecorder(), "token0003"), null);
    assertThat(picker4.pickList).containsExactly(
        new BackendEntry(subchannel, new TokenAttachingTracerFactory(getLoadRecorder())));

    // Subchannel goes IDLE, but PICK_FIRST will not try to reconnect
    deliverSubchannelState(subchannel, ConnectivityStateInfo.forNonError(IDLE));
    inOrder.verify(helper).updateBalancingState(eq(IDLE), pickerCaptor.capture());
    RoundRobinPicker picker5 = (RoundRobinPicker) pickerCaptor.getValue();
    verify(subchannel, never()).requestConnection();

    // ... until it's selected
    PickSubchannelArgs args = mock(PickSubchannelArgs.class);
    PickResult pick = picker5.pickSubchannel(args);
    assertThat(pick).isSameInstanceAs(PickResult.withNoResult());
    verify(subchannel).requestConnection();

    // ... or requested by application
    balancer.requestConnection();
    verify(subchannel, times(2)).requestConnection();

    // PICK_FIRST doesn't use subchannelPool
    verify(subchannelPool, never())
        .takeOrCreateSubchannel(any(EquivalentAddressGroup.class), any(Attributes.class));
    verify(subchannelPool, never())
        .returnSubchannel(any(Subchannel.class), any(ConnectivityStateInfo.class));
  }

  @Test
  public void grpclbWorking_pickFirstMode_lbSendsEmptyAddress() throws Exception {
    InOrder inOrder = inOrder(helper);

    List<EquivalentAddressGroup> grpclbBalancerList = createResolvedBalancerAddresses(1);
    deliverResolvedAddresses(
        Collections.<EquivalentAddressGroup>emptyList(),
        grpclbBalancerList,
        GrpclbConfig.create(Mode.PICK_FIRST));

    assertEquals(1, fakeOobChannels.size());
    verify(mockLbService).balanceLoad(lbResponseObserverCaptor.capture());
    StreamObserver<LoadBalanceResponse> lbResponseObserver = lbResponseObserverCaptor.getValue();
    assertEquals(1, lbRequestObservers.size());
    StreamObserver<LoadBalanceRequest> lbRequestObserver = lbRequestObservers.poll();
    verify(lbRequestObserver).onNext(
        eq(LoadBalanceRequest.newBuilder().setInitialRequest(
            InitialLoadBalanceRequest.newBuilder().setName(SERVICE_AUTHORITY).build())
            .build()));

    // Simulate receiving LB response
    List<ServerEntry> backends1 = Arrays.asList(
        new ServerEntry("127.0.0.1", 2000, "token0001"),
        new ServerEntry("127.0.0.1", 2010, "token0002"));
    inOrder.verify(helper, never())
        .updateBalancingState(any(ConnectivityState.class), any(SubchannelPicker.class));
    lbResponseObserver.onNext(buildInitialResponse());
    lbResponseObserver.onNext(buildLbResponse(backends1));

    inOrder.verify(helper).createSubchannel(createSubchannelArgsCaptor.capture());
    CreateSubchannelArgs createSubchannelArgs = createSubchannelArgsCaptor.getValue();
    assertThat(createSubchannelArgs.getAddresses())
        .containsExactly(
            new EquivalentAddressGroup(backends1.get(0).addr, eagAttrsWithToken("token0001")),
            new EquivalentAddressGroup(backends1.get(1).addr, eagAttrsWithToken("token0002")));

    // Initially IDLE
    inOrder.verify(helper).updateBalancingState(eq(IDLE), pickerCaptor.capture());
    RoundRobinPicker picker0 = (RoundRobinPicker) pickerCaptor.getValue();

    // Only one subchannel is created
    assertThat(mockSubchannels).hasSize(1);
    Subchannel subchannel = mockSubchannels.poll();
    assertThat(picker0.dropList).containsExactly(null, null);
    assertThat(picker0.pickList).containsExactly(new IdleSubchannelEntry(subchannel, syncContext));

    // PICK_FIRST doesn't eagerly connect
    verify(subchannel, never()).requestConnection();

    // CONNECTING
    deliverSubchannelState(subchannel, ConnectivityStateInfo.forNonError(CONNECTING));
    inOrder.verify(helper).updateBalancingState(eq(CONNECTING), pickerCaptor.capture());
    RoundRobinPicker picker1 = (RoundRobinPicker) pickerCaptor.getValue();
    assertThat(picker1.dropList).containsExactly(null, null);
    assertThat(picker1.pickList).containsExactly(BUFFER_ENTRY);

    // TRANSIENT_FAILURE
    Status error = Status.UNAVAILABLE.withDescription("Simulated connection error");
    deliverSubchannelState(subchannel, ConnectivityStateInfo.forTransientFailure(error));
    inOrder.verify(helper).updateBalancingState(eq(TRANSIENT_FAILURE), pickerCaptor.capture());
    RoundRobinPicker picker2 = (RoundRobinPicker) pickerCaptor.getValue();
    assertThat(picker2.dropList).containsExactly(null, null);
    assertThat(picker2.pickList).containsExactly(new ErrorEntry(error));

    // READY
    deliverSubchannelState(subchannel, ConnectivityStateInfo.forNonError(READY));
    inOrder.verify(helper).updateBalancingState(eq(READY), pickerCaptor.capture());
    RoundRobinPicker picker3 = (RoundRobinPicker) pickerCaptor.getValue();
    assertThat(picker3.dropList).containsExactly(null, null);
    assertThat(picker3.pickList).containsExactly(
        new BackendEntry(subchannel, new TokenAttachingTracerFactory(getLoadRecorder())));

    inOrder.verify(helper, never())
        .updateBalancingState(any(ConnectivityState.class), any(SubchannelPicker.class));

    // Empty addresses from LB
    lbResponseObserver.onNext(buildLbResponse(Collections.<ServerEntry>emptyList()));

    // new addresses will be updated to the existing subchannel
    // createSubchannel() has ever been called only once
    inOrder.verify(helper, never()).createSubchannel(any(CreateSubchannelArgs.class));
    assertThat(mockSubchannels).isEmpty();
    verify(subchannel).shutdown();

    // RPC error status includes message of no backends provided by balancer
    inOrder.verify(helper).updateBalancingState(eq(TRANSIENT_FAILURE), pickerCaptor.capture());
    RoundRobinPicker errorPicker = (RoundRobinPicker) pickerCaptor.getValue();
    assertThat(errorPicker.pickList)
        .containsExactly(new ErrorEntry(GrpclbState.NO_AVAILABLE_BACKENDS_STATUS));

    lbResponseObserver.onNext(buildLbResponse(Collections.<ServerEntry>emptyList()));

    // Test recover from new LB response with addresses
    // New server list with drops
    List<ServerEntry> backends2 = Arrays.asList(
        new ServerEntry("127.0.0.1", 2000, "token0001"),
        new ServerEntry("token0003"),  // drop
        new ServerEntry("127.0.0.1", 2020, "token0004"));
    inOrder.verify(helper, never())
        .updateBalancingState(any(ConnectivityState.class), any(SubchannelPicker.class));
    lbResponseObserver.onNext(buildLbResponse(backends2));

    // new addresses will be updated to the existing subchannel
    inOrder.verify(helper, times(1)).createSubchannel(any(CreateSubchannelArgs.class));
    inOrder.verify(helper).updateBalancingState(eq(IDLE), pickerCaptor.capture());
    subchannel = mockSubchannels.poll();

    // Subchannel became READY
    deliverSubchannelState(subchannel, ConnectivityStateInfo.forNonError(CONNECTING));
    deliverSubchannelState(subchannel, ConnectivityStateInfo.forNonError(READY));
    inOrder.verify(helper).updateBalancingState(eq(READY), pickerCaptor.capture());
    RoundRobinPicker picker4 = (RoundRobinPicker) pickerCaptor.getValue();
    assertThat(picker4.pickList).containsExactly(
        new BackendEntry(subchannel, new TokenAttachingTracerFactory(getLoadRecorder())));
  }

  @Test
  public void shutdownWithoutSubchannel_roundRobin() throws Exception {
    subtestShutdownWithoutSubchannel(GrpclbConfig.create(Mode.ROUND_ROBIN));
  }

  @Test
  public void shutdownWithoutSubchannel_pickFirst() throws Exception {
    subtestShutdownWithoutSubchannel(GrpclbConfig.create(Mode.PICK_FIRST));
  }

  private void subtestShutdownWithoutSubchannel(GrpclbConfig grpclbConfig) {
    List<EquivalentAddressGroup> grpclbBalancerList = createResolvedBalancerAddresses(1);
    deliverResolvedAddresses(
        Collections.<EquivalentAddressGroup>emptyList(),
        grpclbBalancerList,
        grpclbConfig);
    verify(mockLbService).balanceLoad(lbResponseObserverCaptor.capture());
    assertEquals(1, lbRequestObservers.size());
    StreamObserver<LoadBalanceRequest> requestObserver = lbRequestObservers.poll();

    verify(requestObserver, never()).onCompleted();
    balancer.shutdown();
    ArgumentCaptor<Throwable> throwableCaptor = ArgumentCaptor.forClass(Throwable.class);
    verify(requestObserver).onError(throwableCaptor.capture());
    assertThat(Status.fromThrowable(throwableCaptor.getValue()).getCode())
        .isEqualTo(Code.CANCELLED);
  }

  @Test
  public void pickFirstMode_defaultTimeout_fallback() throws Exception {
    pickFirstModeFallback(GrpclbState.FALLBACK_TIMEOUT_MS);
  }

  @Test
  public void pickFirstMode_serviceConfigTimeout_fallback() throws Exception {
    pickFirstModeFallback(12345);
  }

  private void pickFirstModeFallback(long timeout) throws Exception {
    InOrder inOrder = inOrder(helper);

    // Name resolver returns balancer and backend addresses
    List<EquivalentAddressGroup> backendList = createResolvedBackendAddresses(2);
    List<EquivalentAddressGroup> grpclbBalancerList = createResolvedBalancerAddresses(1);
    deliverResolvedAddresses(
        backendList, grpclbBalancerList, GrpclbConfig.create(Mode.PICK_FIRST, null, timeout));

    // Attempted to connect to balancer
    assertEquals(1, fakeOobChannels.size());
    verify(mockLbService).balanceLoad(lbResponseObserverCaptor.capture());
    StreamObserver<LoadBalanceResponse> lbResponseObserver = lbResponseObserverCaptor.getValue();
    assertEquals(1, lbRequestObservers.size());

    // Fallback timer expires with no response
    fakeClock.forwardTime(timeout, TimeUnit.MILLISECONDS);

    // Entering fallback mode
    inOrder.verify(helper).createSubchannel(createSubchannelArgsCaptor.capture());
    CreateSubchannelArgs createSubchannelArgs = createSubchannelArgsCaptor.getValue();
    assertThat(createSubchannelArgs.getAddresses())
        .containsExactly(backendList.get(0), backendList.get(1));

    assertThat(mockSubchannels).hasSize(1);
    Subchannel subchannel = mockSubchannels.poll();

    // Initially IDLE
    inOrder.verify(helper).updateBalancingState(eq(IDLE), pickerCaptor.capture());
    RoundRobinPicker picker0 = (RoundRobinPicker) pickerCaptor.getValue();

    // READY
    deliverSubchannelState(subchannel, ConnectivityStateInfo.forNonError(READY));
    inOrder.verify(helper).updateBalancingState(eq(READY), pickerCaptor.capture());
    RoundRobinPicker picker1 = (RoundRobinPicker) pickerCaptor.getValue();
    assertThat(picker1.dropList).containsExactly(null, null);
    assertThat(picker1.pickList).containsExactly(
        new BackendEntry(subchannel, new TokenAttachingTracerFactory(null)));

    assertThat(picker0.dropList).containsExactly(null, null);
    assertThat(picker0.pickList).containsExactly(new IdleSubchannelEntry(subchannel, syncContext));


    // Finally, an LB response, which brings us out of fallback
    List<ServerEntry> backends1 = Arrays.asList(
        new ServerEntry("127.0.0.1", 2000, "token0001"),
        new ServerEntry("127.0.0.1", 2010, "token0002"));
    inOrder.verify(helper, never())
        .updateBalancingState(any(ConnectivityState.class), any(SubchannelPicker.class));
    lbResponseObserver.onNext(buildInitialResponse());
    lbResponseObserver.onNext(buildLbResponse(backends1));

    // new addresses will be updated to the existing subchannel
    // createSubchannel() has ever been called only once
    inOrder.verify(helper, never()).createSubchannel(any(CreateSubchannelArgs.class));
    assertThat(mockSubchannels).isEmpty();
    verify(subchannel).updateAddresses(
        eq(Arrays.asList(
            new EquivalentAddressGroup(backends1.get(0).addr, eagAttrsWithToken("token0001")),
            new EquivalentAddressGroup(backends1.get(1).addr,
                eagAttrsWithToken("token0002")))));
    inOrder.verify(helper).updateBalancingState(eq(READY), pickerCaptor.capture());
    RoundRobinPicker picker2 = (RoundRobinPicker) pickerCaptor.getValue();
    assertThat(picker2.dropList).containsExactly(null, null);
    assertThat(picker2.pickList).containsExactly(
        new BackendEntry(subchannel, new TokenAttachingTracerFactory(getLoadRecorder())));

    // PICK_FIRST doesn't use subchannelPool
    verify(subchannelPool, never())
        .takeOrCreateSubchannel(any(EquivalentAddressGroup.class), any(Attributes.class));
    verify(subchannelPool, never())
        .returnSubchannel(any(Subchannel.class), any(ConnectivityStateInfo.class));
  }

  @Test
  public void switchMode() throws Exception {
    InOrder inOrder = inOrder(helper);

    List<EquivalentAddressGroup> grpclbBalancerList = createResolvedBalancerAddresses(1);
    deliverResolvedAddresses(
        Collections.<EquivalentAddressGroup>emptyList(),
        grpclbBalancerList,
        GrpclbConfig.create(Mode.ROUND_ROBIN));

    assertEquals(1, fakeOobChannels.size());
    ManagedChannel oobChannel = fakeOobChannels.poll();
    verify(mockLbService).balanceLoad(lbResponseObserverCaptor.capture());
    StreamObserver<LoadBalanceResponse> lbResponseObserver = lbResponseObserverCaptor.getValue();
    assertEquals(1, lbRequestObservers.size());
    StreamObserver<LoadBalanceRequest> lbRequestObserver = lbRequestObservers.poll();
    verify(lbRequestObserver).onNext(
        eq(LoadBalanceRequest.newBuilder().setInitialRequest(
            InitialLoadBalanceRequest.newBuilder().setName(SERVICE_AUTHORITY).build())
            .build()));

    // Simulate receiving LB response
    List<ServerEntry> backends1 = Arrays.asList(
        new ServerEntry("127.0.0.1", 2000, "token0001"),
        new ServerEntry("127.0.0.1", 2010, "token0002"));
    inOrder.verify(helper, never())
        .updateBalancingState(any(ConnectivityState.class), any(SubchannelPicker.class));
    lbResponseObserver.onNext(buildInitialResponse());
    lbResponseObserver.onNext(buildLbResponse(backends1));

    // ROUND_ROBIN: create one subchannel per server
    verify(subchannelPool).takeOrCreateSubchannel(
        eq(new EquivalentAddressGroup(backends1.get(0).addr, LB_BACKEND_ATTRS)),
        any(Attributes.class));
    verify(subchannelPool).takeOrCreateSubchannel(
        eq(new EquivalentAddressGroup(backends1.get(1).addr, LB_BACKEND_ATTRS)),
        any(Attributes.class));
    inOrder.verify(helper).updateBalancingState(eq(CONNECTING), any(SubchannelPicker.class));
    assertEquals(2, mockSubchannels.size());
    Subchannel subchannel1 = mockSubchannels.poll();
    Subchannel subchannel2 = mockSubchannels.poll();
    verify(subchannelPool, never())
        .returnSubchannel(any(Subchannel.class), any(ConnectivityStateInfo.class));

    // Switch to PICK_FIRST
    deliverResolvedAddresses(
        Collections.<EquivalentAddressGroup>emptyList(),
        grpclbBalancerList, GrpclbConfig.create(Mode.PICK_FIRST));


    // GrpclbState will be shutdown, and a new one will be created
    assertThat(oobChannel.isShutdown()).isTrue();
    verify(subchannelPool)
        .returnSubchannel(same(subchannel1), eq(ConnectivityStateInfo.forNonError(IDLE)));
    verify(subchannelPool)
        .returnSubchannel(same(subchannel2), eq(ConnectivityStateInfo.forNonError(IDLE)));

    // A new LB stream is created
    assertEquals(1, fakeOobChannels.size());
    verify(mockLbService, times(2)).balanceLoad(lbResponseObserverCaptor.capture());
    lbResponseObserver = lbResponseObserverCaptor.getValue();
    assertEquals(1, lbRequestObservers.size());
    lbRequestObserver = lbRequestObservers.poll();
    verify(lbRequestObserver).onNext(
        eq(LoadBalanceRequest.newBuilder().setInitialRequest(
            InitialLoadBalanceRequest.newBuilder().setName(SERVICE_AUTHORITY).build())
            .build()));

    // Simulate receiving LB response
    inOrder.verify(helper, never())
        .updateBalancingState(any(ConnectivityState.class), any(SubchannelPicker.class));
    lbResponseObserver.onNext(buildInitialResponse());
    lbResponseObserver.onNext(buildLbResponse(backends1));

    // PICK_FIRST Subchannel
    inOrder.verify(helper).createSubchannel(createSubchannelArgsCaptor.capture());
    CreateSubchannelArgs createSubchannelArgs = createSubchannelArgsCaptor.getValue();
    assertThat(createSubchannelArgs.getAddresses())
        .containsExactly(
            new EquivalentAddressGroup(backends1.get(0).addr, eagAttrsWithToken("token0001")),
            new EquivalentAddressGroup(backends1.get(1).addr, eagAttrsWithToken("token0002")));

    inOrder.verify(helper).updateBalancingState(eq(IDLE), any(SubchannelPicker.class));
  }

  private static Attributes eagAttrsWithToken(String token) {
    return LB_BACKEND_ATTRS.toBuilder().set(GrpclbConstants.TOKEN_ATTRIBUTE_KEY, token).build();
  }

  @Test
  public void switchMode_nullLbPolicy() throws Exception {
    InOrder inOrder = inOrder(helper);

    final List<EquivalentAddressGroup> grpclbBalancerList = createResolvedBalancerAddresses(1);
    deliverResolvedAddresses(
        Collections.<EquivalentAddressGroup>emptyList(),
        grpclbBalancerList);

    assertEquals(1, fakeOobChannels.size());
    ManagedChannel oobChannel = fakeOobChannels.poll();
    verify(mockLbService).balanceLoad(lbResponseObserverCaptor.capture());
    StreamObserver<LoadBalanceResponse> lbResponseObserver = lbResponseObserverCaptor.getValue();
    assertEquals(1, lbRequestObservers.size());
    StreamObserver<LoadBalanceRequest> lbRequestObserver = lbRequestObservers.poll();
    verify(lbRequestObserver).onNext(
        eq(LoadBalanceRequest.newBuilder().setInitialRequest(
            InitialLoadBalanceRequest.newBuilder().setName(SERVICE_AUTHORITY).build())
            .build()));

    // Simulate receiving LB response
    List<ServerEntry> backends1 = Arrays.asList(
        new ServerEntry("127.0.0.1", 2000, "token0001"),
        new ServerEntry("127.0.0.1", 2010, "token0002"));
    inOrder.verify(helper, never())
        .updateBalancingState(any(ConnectivityState.class), any(SubchannelPicker.class));
    lbResponseObserver.onNext(buildInitialResponse());
    lbResponseObserver.onNext(buildLbResponse(backends1));

    // ROUND_ROBIN: create one subchannel per server
    verify(subchannelPool).takeOrCreateSubchannel(
        eq(new EquivalentAddressGroup(backends1.get(0).addr, LB_BACKEND_ATTRS)),
        any(Attributes.class));
    verify(subchannelPool).takeOrCreateSubchannel(
        eq(new EquivalentAddressGroup(backends1.get(1).addr, LB_BACKEND_ATTRS)),
        any(Attributes.class));
    inOrder.verify(helper).updateBalancingState(eq(CONNECTING), any(SubchannelPicker.class));
    assertEquals(2, mockSubchannels.size());
    Subchannel subchannel1 = mockSubchannels.poll();
    Subchannel subchannel2 = mockSubchannels.poll();
    verify(subchannelPool, never())
        .returnSubchannel(any(Subchannel.class), any(ConnectivityStateInfo.class));

    // Switch to PICK_FIRST
    deliverResolvedAddresses(
        Collections.<EquivalentAddressGroup>emptyList(),
        grpclbBalancerList,
        GrpclbConfig.create(Mode.PICK_FIRST));

    // GrpclbState will be shutdown, and a new one will be created
    assertThat(oobChannel.isShutdown()).isTrue();
    verify(subchannelPool)
        .returnSubchannel(same(subchannel1), eq(ConnectivityStateInfo.forNonError(IDLE)));
    verify(subchannelPool)
        .returnSubchannel(same(subchannel2), eq(ConnectivityStateInfo.forNonError(IDLE)));

    // A new LB stream is created
    assertEquals(1, fakeOobChannels.size());
    verify(mockLbService, times(2)).balanceLoad(lbResponseObserverCaptor.capture());
    lbResponseObserver = lbResponseObserverCaptor.getValue();
    assertEquals(1, lbRequestObservers.size());
    lbRequestObserver = lbRequestObservers.poll();
    verify(lbRequestObserver).onNext(
        eq(LoadBalanceRequest.newBuilder().setInitialRequest(
            InitialLoadBalanceRequest.newBuilder().setName(SERVICE_AUTHORITY).build())
            .build()));

    // Simulate receiving LB response
    inOrder.verify(helper, never())
        .updateBalancingState(any(ConnectivityState.class), any(SubchannelPicker.class));
    lbResponseObserver.onNext(buildInitialResponse());
    lbResponseObserver.onNext(buildLbResponse(backends1));

    // PICK_FIRST Subchannel
    inOrder.verify(helper).createSubchannel(createSubchannelArgsCaptor.capture());
    CreateSubchannelArgs createSubchannelArgs = createSubchannelArgsCaptor.getValue();
    assertThat(createSubchannelArgs.getAddresses())
        .containsExactly(
            new EquivalentAddressGroup(backends1.get(0).addr, eagAttrsWithToken("token0001")),
            new EquivalentAddressGroup(backends1.get(1).addr, eagAttrsWithToken("token0002")));

    inOrder.verify(helper).updateBalancingState(eq(IDLE), any(SubchannelPicker.class));
  }

  @Test
  public void switchServiceName() throws Exception {
    InOrder inOrder = inOrder(helper);

    String serviceName = "foo.google.com";
    List<EquivalentAddressGroup> grpclbBalancerList = createResolvedBalancerAddresses(1);

    deliverResolvedAddresses(
        Collections.<EquivalentAddressGroup>emptyList(),
        grpclbBalancerList,
        GrpclbConfig.create(Mode.ROUND_ROBIN, serviceName, GrpclbState.FALLBACK_TIMEOUT_MS));

    assertEquals(1, fakeOobChannels.size());
    ManagedChannel oobChannel = fakeOobChannels.poll();
    verify(mockLbService).balanceLoad(lbResponseObserverCaptor.capture());
    StreamObserver<LoadBalanceResponse> lbResponseObserver = lbResponseObserverCaptor.getValue();
    assertEquals(1, lbRequestObservers.size());
    StreamObserver<LoadBalanceRequest> lbRequestObserver = lbRequestObservers.poll();
    verify(lbRequestObserver).onNext(
        eq(LoadBalanceRequest.newBuilder().setInitialRequest(
            InitialLoadBalanceRequest.newBuilder().setName(serviceName).build())
            .build()));

    // Simulate receiving LB response
    List<ServerEntry> backends1 = Arrays.asList(
        new ServerEntry("127.0.0.1", 2000, "token0001"),
        new ServerEntry("127.0.0.1", 2010, "token0002"));
    inOrder.verify(helper, never())
        .updateBalancingState(any(ConnectivityState.class), any(SubchannelPicker.class));
    lbResponseObserver.onNext(buildInitialResponse());
    lbResponseObserver.onNext(buildLbResponse(backends1));

    // ROUND_ROBIN: create one subchannel per server
    verify(subchannelPool).takeOrCreateSubchannel(
        eq(new EquivalentAddressGroup(backends1.get(0).addr, LB_BACKEND_ATTRS)),
        any(Attributes.class));
    verify(subchannelPool).takeOrCreateSubchannel(
        eq(new EquivalentAddressGroup(backends1.get(1).addr, LB_BACKEND_ATTRS)),
        any(Attributes.class));
    inOrder.verify(helper).updateBalancingState(eq(CONNECTING), any(SubchannelPicker.class));
    assertEquals(2, mockSubchannels.size());
    Subchannel subchannel1 = mockSubchannels.poll();
    Subchannel subchannel2 = mockSubchannels.poll();
    verify(subchannelPool, never())
        .returnSubchannel(any(Subchannel.class), any(ConnectivityStateInfo.class));

    // Switch to different serviceName
    serviceName = "bar.google.com";
    List<EquivalentAddressGroup> newGrpclbResolutionList = createResolvedBalancerAddresses(1);
    deliverResolvedAddresses(
        Collections.<EquivalentAddressGroup>emptyList(),
        newGrpclbResolutionList,
        GrpclbConfig.create(Mode.ROUND_ROBIN, serviceName, GrpclbState.FALLBACK_TIMEOUT_MS));

    // GrpclbState will be shutdown, and a new one will be created
    assertThat(oobChannel.isShutdown()).isTrue();
    verify(subchannelPool)
        .returnSubchannel(same(subchannel1), eq(ConnectivityStateInfo.forNonError(IDLE)));
    verify(subchannelPool)
        .returnSubchannel(same(subchannel2), eq(ConnectivityStateInfo.forNonError(IDLE)));

    assertEquals(1, fakeOobChannels.size());
    verify(mockLbService, times(2)).balanceLoad(lbResponseObserverCaptor.capture());
    assertEquals(1, lbRequestObservers.size());
    lbRequestObserver = lbRequestObservers.poll();
    verify(lbRequestObserver).onNext(
        eq(LoadBalanceRequest.newBuilder().setInitialRequest(
            InitialLoadBalanceRequest.newBuilder().setName(serviceName).build())
            .build()));
  }

  @Test
  public void grpclbWorking_lbSendsFallbackMessage() {
    InOrder inOrder = inOrder(helper, subchannelPool);
    List<EquivalentAddressGroup> backendList = createResolvedBackendAddresses(2);
    List<EquivalentAddressGroup> grpclbBalancerList = createResolvedBalancerAddresses(2);
    deliverResolvedAddresses(backendList, grpclbBalancerList);

    // Fallback timer is started as soon as the addresses are resolved.
    assertEquals(1, fakeClock.numPendingTasks(FALLBACK_MODE_TASK_FILTER));
    verify(helper).createOobChannel(eq(xattr(grpclbBalancerList)),
        eq(lbAuthority(0) + NO_USE_AUTHORITY_SUFFIX));
    assertEquals(1, fakeOobChannels.size());
    ManagedChannel oobChannel = fakeOobChannels.poll();
    verify(mockLbService).balanceLoad(lbResponseObserverCaptor.capture());
    StreamObserver<LoadBalanceResponse> lbResponseObserver = lbResponseObserverCaptor.getValue();
    assertEquals(1, lbRequestObservers.size());
    StreamObserver<LoadBalanceRequest> lbRequestObserver = lbRequestObservers.poll();
    verify(lbRequestObserver).onNext(
        eq(LoadBalanceRequest.newBuilder()
            .setInitialRequest(
                InitialLoadBalanceRequest.newBuilder().setName(SERVICE_AUTHORITY).build())
            .build()));

    // Simulate receiving LB response
    ServerEntry backend1a = new ServerEntry("127.0.0.1", 2000, "token0001");
    ServerEntry backend1b = new ServerEntry("127.0.0.1", 2010, "token0002");
    List<ServerEntry> backends1 = Arrays.asList(backend1a, backend1b);
    inOrder.verify(helper, never())
        .updateBalancingState(any(ConnectivityState.class), any(SubchannelPicker.class));
    logs.clear();
    lbResponseObserver.onNext(buildInitialResponse());
    assertThat(logs).containsExactly(
        "INFO: [grpclb-<api.google.com>] Got an LB initial response: " + buildInitialResponse());
    logs.clear();
    lbResponseObserver.onNext(buildLbResponse(backends1));

    inOrder.verify(subchannelPool).takeOrCreateSubchannel(
        eq(new EquivalentAddressGroup(backend1a.addr, LB_BACKEND_ATTRS)),
        any(Attributes.class));
    inOrder.verify(subchannelPool).takeOrCreateSubchannel(
        eq(new EquivalentAddressGroup(backend1b.addr, LB_BACKEND_ATTRS)),
        any(Attributes.class));

    assertEquals(2, mockSubchannels.size());
    Subchannel subchannel1 = mockSubchannels.poll();
    Subchannel subchannel2 = mockSubchannels.poll();

    verify(subchannel1).requestConnection();
    verify(subchannel2).requestConnection();
    assertEquals(
        new EquivalentAddressGroup(backend1a.addr, LB_BACKEND_ATTRS),
        subchannel1.getAddresses());
    assertEquals(
        new EquivalentAddressGroup(backend1b.addr, LB_BACKEND_ATTRS),
        subchannel2.getAddresses());

    deliverSubchannelState(subchannel1, ConnectivityStateInfo.forNonError(CONNECTING));
    deliverSubchannelState(subchannel2, ConnectivityStateInfo.forNonError(CONNECTING));

    inOrder.verify(helper).updateBalancingState(eq(CONNECTING), pickerCaptor.capture());
    RoundRobinPicker picker0 = (RoundRobinPicker) pickerCaptor.getValue();
    assertThat(picker0.dropList).containsExactly(null, null);
    assertThat(picker0.pickList).containsExactly(BUFFER_ENTRY);
    inOrder.verifyNoMoreInteractions();

    assertThat(logs)
        .containsExactly(
            "DEBUG: [grpclb-<api.google.com>] Got an LB response: " + buildLbResponse(backends1))
        .inOrder();
    logs.clear();

    // Let subchannels be connected
    deliverSubchannelState(subchannel2, ConnectivityStateInfo.forNonError(READY));
    inOrder.verify(helper).updateBalancingState(eq(READY), pickerCaptor.capture());

    RoundRobinPicker picker1 = (RoundRobinPicker) pickerCaptor.getValue();

    assertThat(picker1.dropList).containsExactly(null, null);
    assertThat(picker1.pickList).containsExactly(
        new BackendEntry(subchannel2, getLoadRecorder(), "token0002"));

    deliverSubchannelState(subchannel1, ConnectivityStateInfo.forNonError(READY));
    inOrder.verify(helper).updateBalancingState(eq(READY), pickerCaptor.capture());

    RoundRobinPicker picker2 = (RoundRobinPicker) pickerCaptor.getValue();
    assertThat(picker2.dropList).containsExactly(null, null);
    assertThat(picker2.pickList).containsExactly(
        new BackendEntry(subchannel1, getLoadRecorder(), "token0001"),
        new BackendEntry(subchannel2, getLoadRecorder(), "token0002"))
        .inOrder();

    // Balancer forces entering fallback mode
    lbResponseObserver.onNext(buildLbFallbackResponse());

    // existing subchannels must be returned immediately to gracefully shutdown.
    verify(subchannelPool)
        .returnSubchannel(eq(subchannel1), eq(ConnectivityStateInfo.forNonError(READY)));
    verify(subchannelPool)
        .returnSubchannel(eq(subchannel2), eq(ConnectivityStateInfo.forNonError(READY)));

    // verify fallback
    fallbackTestVerifyUseOfFallbackBackendLists(inOrder, backendList);

    assertFalse(oobChannel.isShutdown());
    verify(lbRequestObserver, never()).onCompleted();

    //////////////////////////////////////////////////////////////////////
    // Name resolver sends new resolution results without any backend addr
    //////////////////////////////////////////////////////////////////////
    deliverResolvedAddresses(Collections.<EquivalentAddressGroup>emptyList(), grpclbBalancerList);

    // Still in fallback logic, except that the backend list is empty
    for (Subchannel subchannel : mockSubchannels) {
      verify(subchannelPool).returnSubchannel(eq(subchannel), any(ConnectivityStateInfo.class));
    }

    // RPC error status includes message of fallback requested by balancer
    inOrder.verify(helper).updateBalancingState(eq(TRANSIENT_FAILURE), pickerCaptor.capture());
    PickResult result = pickerCaptor.getValue().pickSubchannel(mock(PickSubchannelArgs.class));
    assertThat(result.getStatus().getCode()).isEqualTo(Code.UNAVAILABLE);
    assertThat(result.getStatus().getDescription())
        .startsWith(GrpclbState.NO_FALLBACK_BACKENDS_STATUS.getDescription());
    assertThat(result.getStatus().getDescription())
        .contains(GrpclbState.BALANCER_REQUESTED_FALLBACK_STATUS.getDescription());

    // exit fall back by providing two new backends
    ServerEntry backend2a = new ServerEntry("127.0.0.1", 8000, "token1001");
    ServerEntry backend2b = new ServerEntry("127.0.0.1", 8010, "token1002");
    List<ServerEntry> backends2 = Arrays.asList(backend2a, backend2b);

    inOrder.verify(helper, never())
        .updateBalancingState(any(ConnectivityState.class), any(SubchannelPicker.class));
    logs.clear();
    lbResponseObserver.onNext(buildLbResponse(backends2));

    inOrder.verify(subchannelPool).takeOrCreateSubchannel(
        eq(new EquivalentAddressGroup(backend2a.addr, LB_BACKEND_ATTRS)),
        any(Attributes.class));
    inOrder.verify(subchannelPool).takeOrCreateSubchannel(
        eq(new EquivalentAddressGroup(backend2b.addr, LB_BACKEND_ATTRS)),
        any(Attributes.class));

    assertEquals(2, mockSubchannels.size());
    Subchannel subchannel3 = mockSubchannels.poll();
    Subchannel subchannel4 = mockSubchannels.poll();
    verify(subchannel3).requestConnection();
    verify(subchannel4).requestConnection();
    assertEquals(
        new EquivalentAddressGroup(backend2a.addr, LB_BACKEND_ATTRS),
        subchannel3.getAddresses());
    assertEquals(
        new EquivalentAddressGroup(backend2b.addr, LB_BACKEND_ATTRS),
        subchannel4.getAddresses());

    deliverSubchannelState(subchannel3, ConnectivityStateInfo.forNonError(CONNECTING));
    deliverSubchannelState(subchannel4, ConnectivityStateInfo.forNonError(CONNECTING));

    inOrder.verify(helper).updateBalancingState(eq(CONNECTING), pickerCaptor.capture());
    RoundRobinPicker picker6 = (RoundRobinPicker) pickerCaptor.getValue();
    assertThat(picker6.dropList).containsExactly(null, null);
    assertThat(picker6.pickList).containsExactly(BUFFER_ENTRY);
    inOrder.verifyNoMoreInteractions();

    assertThat(logs)
        .containsExactly(
            "DEBUG: [grpclb-<api.google.com>] Got an LB response: " + buildLbResponse(backends2))
        .inOrder();
    logs.clear();

    // Let new subchannels be connected
    deliverSubchannelState(subchannel3, ConnectivityStateInfo.forNonError(READY));
    inOrder.verify(helper).updateBalancingState(eq(READY), pickerCaptor.capture());

    RoundRobinPicker picker3 = (RoundRobinPicker) pickerCaptor.getValue();
    assertThat(picker3.dropList).containsExactly(null, null);
    assertThat(picker3.pickList).containsExactly(
        new BackendEntry(subchannel3, getLoadRecorder(), "token1001"));

    deliverSubchannelState(subchannel4, ConnectivityStateInfo.forNonError(READY));
    inOrder.verify(helper).updateBalancingState(eq(READY), pickerCaptor.capture());

    RoundRobinPicker picker4 = (RoundRobinPicker) pickerCaptor.getValue();
    assertThat(picker4.dropList).containsExactly(null, null);
    assertThat(picker4.pickList).containsExactly(
        new BackendEntry(subchannel3, getLoadRecorder(), "token1001"),
        new BackendEntry(subchannel4, getLoadRecorder(), "token1002"))
        .inOrder();
  }

  @Test
  public void useIndependentRpcContext() {
    // Simulates making RPCs within the context of an inbound RPC.
    CancellableContext cancellableContext = Context.current().withCancellation();
    Context prevContext = cancellableContext.attach();
    try {
      List<EquivalentAddressGroup> backendList = createResolvedBackendAddresses(2);
      List<EquivalentAddressGroup> grpclbBalancerList = createResolvedBalancerAddresses(2);
      deliverResolvedAddresses(backendList, grpclbBalancerList);

      verify(helper).createOobChannel(eq(xattr(grpclbBalancerList)),
          eq(lbAuthority(0) + NO_USE_AUTHORITY_SUFFIX));
      verify(mockLbService).balanceLoad(lbResponseObserverCaptor.capture());
      StreamObserver<LoadBalanceResponse> lbResponseObserver = lbResponseObserverCaptor.getValue();
      assertEquals(1, lbRequestObservers.size());
      StreamObserver<LoadBalanceRequest> lbRequestObserver = lbRequestObservers.poll();
      verify(lbRequestObserver).onNext(
          eq(LoadBalanceRequest.newBuilder()
              .setInitialRequest(
                  InitialLoadBalanceRequest.newBuilder().setName(SERVICE_AUTHORITY).build())
              .build()));
      lbResponseObserver.onNext(buildInitialResponse());

      // The inbound RPC finishes and closes its context. The outbound RPC's control plane RPC
      // should not be impacted (no retry).
      cancellableContext.close();
      assertEquals(0, fakeClock.numPendingTasks(LB_RPC_RETRY_TASK_FILTER));
      verifyNoMoreInteractions(mockLbService);
    } finally {
      cancellableContext.detach(prevContext);
    }
  }

  private void deliverSubchannelState(
      final Subchannel subchannel, final ConnectivityStateInfo newState) {
    ((FakeSubchannel) subchannel).updateState(newState);
  }

  private void deliverNameResolutionError(final Status error) {
    syncContext.execute(new Runnable() {
      @Override
      public void run() {
        balancer.handleNameResolutionError(error);
      }
    });
  }

  private void deliverResolvedAddresses(
      final List<EquivalentAddressGroup> backendAddrs,
      List<EquivalentAddressGroup> balancerAddrs) {
    deliverResolvedAddresses(backendAddrs, balancerAddrs, GrpclbConfig.create(Mode.ROUND_ROBIN));
  }

  private void deliverResolvedAddresses(
      final List<EquivalentAddressGroup> backendAddrs,
      List<EquivalentAddressGroup> balancerAddrs,
      final GrpclbConfig grpclbConfig) {
    final Attributes attrs =
        Attributes.newBuilder().set(GrpclbConstants.ATTR_LB_ADDRS, balancerAddrs).build();
    syncContext.execute(new Runnable() {
      @Override
      public void run() {
        balancer.acceptResolvedAddresses(
            ResolvedAddresses.newBuilder()
                .setAddresses(backendAddrs)
                .setAttributes(attrs)
                .setLoadBalancingPolicyConfig(grpclbConfig)
                .build());
      }
    });
  }

  private GrpclbClientLoadRecorder getLoadRecorder() {
    return balancer.getGrpclbState().getLoadRecorder();
  }

  private static List<EquivalentAddressGroup> createResolvedBackendAddresses(int n) {
    List<EquivalentAddressGroup> list = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      SocketAddress addr = new FakeSocketAddress("fake-address-" + i);
      list.add(new EquivalentAddressGroup(addr));
    }
    return list;
  }

  private static List<EquivalentAddressGroup> createResolvedBalancerAddresses(int n) {
    List<EquivalentAddressGroup> list = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      SocketAddress addr = new FakeSocketAddress("fake-address-" + i);
      list.add(new EquivalentAddressGroup(addr, lbAttributes(lbAuthority(i))));
    }
    return list;
  }

  private static String lbAuthority(int unused) {
    // TODO(ejona): Support varying authorities
    return "lb.google.com";
  }

  private static Attributes lbAttributes(String authority) {
    return Attributes.newBuilder()
        .set(GrpclbConstants.ATTR_LB_ADDR_AUTHORITY, authority)
        .build();
  }

  private static LoadBalanceResponse buildInitialResponse() {
    return buildInitialResponse(0);
  }

  private static LoadBalanceResponse buildInitialResponse(long loadReportIntervalMillis) {
    return LoadBalanceResponse.newBuilder()
        .setInitialResponse(
            InitialLoadBalanceResponse.newBuilder()
                .setClientStatsReportInterval(Durations.fromMillis(loadReportIntervalMillis)))
        .build();
  }

  private static LoadBalanceResponse buildLbFallbackResponse() {
    return LoadBalanceResponse.newBuilder()
        .setFallbackResponse(FallbackResponse.newBuilder().build())
        .build();
  }

  private static LoadBalanceResponse buildLbResponse(List<ServerEntry> servers) {
    ServerList.Builder serverListBuilder = ServerList.newBuilder();
    for (ServerEntry server : servers) {
      if (server.addr != null) {
        serverListBuilder.addServers(Server.newBuilder()
            .setIpAddress(ByteString.copyFrom(server.addr.getAddress().getAddress()))
            .setPort(server.addr.getPort())
            .setLoadBalanceToken(server.token)
            .build());
      } else {
        serverListBuilder.addServers(Server.newBuilder()
            .setDrop(true)
            .setLoadBalanceToken(server.token)
            .build());
      }
    }
    return LoadBalanceResponse.newBuilder()
        .setServerList(serverListBuilder.build())
        .build();
  }

  private List<EquivalentAddressGroup> xattr(List<EquivalentAddressGroup> lbAddr) {
    List<EquivalentAddressGroup> oobAddr = new ArrayList<>(lbAddr.size());
    for (EquivalentAddressGroup lb : lbAddr) {
      String authority = lb.getAttributes().get(GrpclbConstants.ATTR_LB_ADDR_AUTHORITY);
      Attributes attrs = lb.getAttributes().toBuilder()
          .set(EquivalentAddressGroup.ATTR_AUTHORITY_OVERRIDE, authority)
          .build();
      oobAddr.add(new EquivalentAddressGroup(lb.getAddresses(), attrs));
    }
    return oobAddr;
  }

  private static class ServerEntry {
    final InetSocketAddress addr;
    final String token;

    ServerEntry(String host, int port, String token) {
      this.addr = new InetSocketAddress(host, port);
      this.token = token;
    }

    // Drop entry
    ServerEntry(String token) {
      this.addr = null;
      this.token = token;
    }
  }

  private static class FakeSubchannel extends Subchannel {
    private final Attributes attributes;
    private List<EquivalentAddressGroup> eags;
    private SubchannelStateListener listener;

    public FakeSubchannel(List<EquivalentAddressGroup> eags, Attributes attributes) {
      this.eags = Collections.unmodifiableList(eags);
      this.attributes = attributes;
    }

    @Override
    public List<EquivalentAddressGroup> getAllAddresses() {
      return eags;
    }

    @Override
    public Attributes getAttributes() {
      return attributes;
    }

    @Override
    public void start(SubchannelStateListener listener) {
      this.listener = checkNotNull(listener, "listener");
    }

    @Override
    public void updateAddresses(List<EquivalentAddressGroup> addrs) {
      this.eags = Collections.unmodifiableList(addrs);
    }

    @Override
    public void shutdown() {
    }

    @Override
    public void requestConnection() {
    }

    public void updateState(ConnectivityStateInfo newState) {
      listener.onSubchannelState(newState);
    }
  }

  private class FakeHelper extends Helper {

    @Override
    public SynchronizationContext getSynchronizationContext() {
      return syncContext;
    }

    @Override
    public ManagedChannel createOobChannel(List<EquivalentAddressGroup> eag, String authority) {
      ManagedChannel channel =
          InProcessChannelBuilder
              .forName("fakeLb")
              .directExecutor()
              .overrideAuthority(authority)
              .build();
      fakeOobChannels.add(channel);
      oobChannelTracker.add(channel);
      return channel;
    }

    @Override
    public ManagedChannel createOobChannel(EquivalentAddressGroup eag, String authority) {
      return createOobChannel(Collections.singletonList(eag), authority);
    }

    @Override
    public Subchannel createSubchannel(CreateSubchannelArgs args) {
      FakeSubchannel subchannel =
          mock(
              FakeSubchannel.class,
              AdditionalAnswers
                  .delegatesTo(new FakeSubchannel(args.getAddresses(), args.getAttributes())));
      mockSubchannels.add(subchannel);
      unpooledSubchannelTracker.add(subchannel);
      return subchannel;
    }

    @Override
    public ScheduledExecutorService getScheduledExecutorService() {
      return fakeClock.getScheduledExecutorService();
    }

    @Override
    public ChannelLogger getChannelLogger() {
      return channelLogger;
    }

    @Override
    public void updateBalancingState(
        @Nonnull ConnectivityState newState, @Nonnull SubchannelPicker newPicker) {
      currentPicker = newPicker;
    }

    @Override
    public void refreshNameResolution() {
    }

    @Override
    public String getAuthority() {
      return SERVICE_AUTHORITY;
    }

    @Override
    public void updateOobChannelAddresses(ManagedChannel channel, EquivalentAddressGroup eag) {
    }

    @Override
    public void updateOobChannelAddresses(ManagedChannel channel,
        List<EquivalentAddressGroup> eag) {
    }
  }
}
