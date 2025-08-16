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

package io.grpc.internal;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.AdditionalAnswers.delegatesTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.google.common.base.Preconditions;
import io.grpc.Attributes;
import io.grpc.ChannelLogger;
import io.grpc.ChannelLogger.ChannelLogLevel;
import io.grpc.ConnectivityState;
import io.grpc.ConnectivityStateInfo;
import io.grpc.EquivalentAddressGroup;
import io.grpc.LoadBalancer;
import io.grpc.LoadBalancer.CreateSubchannelArgs;
import io.grpc.LoadBalancer.Helper;
import io.grpc.LoadBalancer.ResolvedAddresses;
import io.grpc.LoadBalancer.Subchannel;
import io.grpc.LoadBalancer.SubchannelPicker;
import io.grpc.LoadBalancer.SubchannelStateListener;
import io.grpc.LoadBalancerProvider;
import io.grpc.LoadBalancerRegistry;
import io.grpc.NameResolver.ConfigOrError;
import io.grpc.Status;
import io.grpc.SynchronizationContext;
import io.grpc.internal.AutoConfiguredLoadBalancerFactory.AutoConfiguredLoadBalancer;
import io.grpc.internal.PickFirstLeafLoadBalancer.PickFirstLeafLoadBalancerConfig;
import io.grpc.internal.PickFirstLoadBalancer.PickFirstLoadBalancerConfig;
import io.grpc.internal.ServiceConfigUtil.PolicySelection;
import io.grpc.util.ForwardingLoadBalancerHelper;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;

/**
 * Unit tests for {@link AutoConfiguredLoadBalancerFactory}.
 */
@RunWith(JUnit4.class)
// TODO(creamsoup) remove backward compatible check when fully migrated
@SuppressWarnings("deprecation")
public class AutoConfiguredLoadBalancerFactoryTest {
  private static final LoadBalancerRegistry defaultRegistry =
      LoadBalancerRegistry.getDefaultRegistry();
  private final AutoConfiguredLoadBalancerFactory lbf =
      new AutoConfiguredLoadBalancerFactory(GrpcUtil.DEFAULT_LB_POLICY);

  private final ChannelLogger channelLogger = mock(ChannelLogger.class);
  private final LoadBalancer testLbBalancer = mock(LoadBalancer.class);
  private final LoadBalancer testLbBalancer2 = mock(LoadBalancer.class);
  private final AtomicReference<ConfigOrError> nextParsedConfigOrError =
      new AtomicReference<>(ConfigOrError.fromConfig("default"));
  private final AtomicReference<ConfigOrError> nextParsedConfigOrError2 =
      new AtomicReference<>(ConfigOrError.fromConfig("default2"));
  private final FakeLoadBalancerProvider testLbBalancerProvider =
      mock(FakeLoadBalancerProvider.class,
          delegatesTo(
              new FakeLoadBalancerProvider("test_lb", testLbBalancer, nextParsedConfigOrError)));
  private final FakeLoadBalancerProvider testLbBalancerProvider2 =
      mock(FakeLoadBalancerProvider.class,
          delegatesTo(
              new FakeLoadBalancerProvider("test_lb2", testLbBalancer2, nextParsedConfigOrError2)));

  private final Class<? extends LoadBalancer> pfLbClass =
      PickFirstLoadBalancerProvider.isEnabledNewPickFirst()
          ? PickFirstLeafLoadBalancer.class
          : PickFirstLoadBalancer.class;

  @Before
  public void setUp() {
    when(testLbBalancer.acceptResolvedAddresses(isA(ResolvedAddresses.class))).thenReturn(
        Status.OK);
    when(testLbBalancer2.acceptResolvedAddresses(isA(ResolvedAddresses.class))).thenReturn(
        Status.OK);
    defaultRegistry.register(testLbBalancerProvider);
    defaultRegistry.register(testLbBalancerProvider2);
  }

  @After
  public void tearDown() {
    defaultRegistry.deregister(testLbBalancerProvider);
    defaultRegistry.deregister(testLbBalancerProvider2);
  }

  @Test
  public void newLoadBalancer_isAuto() {
    AutoConfiguredLoadBalancer lb = lbf.newLoadBalancer(new TestHelper());

    assertThat(lb).isInstanceOf(AutoConfiguredLoadBalancer.class);
  }

  @Test
  public void defaultIsPickFirst() {
    AutoConfiguredLoadBalancer lb = lbf.newLoadBalancer(new TestHelper());

    assertThat(lb.getDelegateProvider()).isInstanceOf(PickFirstLoadBalancerProvider.class);
    assertThat(lb.getDelegate().getClass().getName()).contains("PickFirst");
  }

  @Test
  public void defaultIsConfigurable() {
    AutoConfiguredLoadBalancer lb = new AutoConfiguredLoadBalancerFactory("test_lb")
        .newLoadBalancer(new TestHelper());

    assertThat(lb.getDelegateProvider()).isSameInstanceAs(testLbBalancerProvider);
    assertThat(lb.getDelegate()).isSameInstanceAs(testLbBalancer);
  }

  @SuppressWarnings("deprecation")
  @Test
  public void forwardsCalls() {
    AutoConfiguredLoadBalancer lb = lbf.newLoadBalancer(new TestHelper());

    final AtomicInteger calls = new AtomicInteger();
    TestLoadBalancer testlb = new TestLoadBalancer() {

      @Override
      public void handleNameResolutionError(Status error) {
        calls.getAndSet(1);
      }

      @Override
      public void handleSubchannelState(Subchannel subchannel, ConnectivityStateInfo stateInfo) {
        calls.getAndSet(2);
      }

      @Override
      public void shutdown() {
        calls.getAndSet(3);
      }
    };

    lb.setDelegate(testlb);

    lb.handleNameResolutionError(Status.RESOURCE_EXHAUSTED);
    assertThat(calls.getAndSet(0)).isEqualTo(1);

    lb.handleSubchannelState(null, null);
    assertThat(calls.getAndSet(0)).isEqualTo(2);

    lb.shutdown();
    assertThat(calls.getAndSet(0)).isEqualTo(3);
  }

  @Test
  public void acceptResolvedAddresses_keepOldBalancer() {
    final List<EquivalentAddressGroup> servers =
        Collections.singletonList(new EquivalentAddressGroup(new SocketAddress(){}));
    Helper helper = new TestHelper() {
      @Override
      public Subchannel createSubchannel(CreateSubchannelArgs args) {
        assertThat(args.getAddresses()).isEqualTo(servers);
        return new TestSubchannel(args);
      }
    };
    AutoConfiguredLoadBalancer lb = lbf.newLoadBalancer(helper);
    LoadBalancer oldDelegate = lb.getDelegate();

    Status addressAcceptanceStatus = lb.tryAcceptResolvedAddresses(
        ResolvedAddresses.newBuilder()
            .setAddresses(servers)
            .setAttributes(Attributes.EMPTY)
            .setLoadBalancingPolicyConfig(null)
            .build());

    assertThat(addressAcceptanceStatus.isOk()).isTrue();
    assertThat(lb.getDelegate()).isSameInstanceAs(oldDelegate);
  }

  @Test
  public void acceptResolvedAddresses_shutsDownOldBalancer() throws Exception {
    Map<String, ?> serviceConfig =
        parseConfig("{\"loadBalancingConfig\": [ {\"round_robin\": { } } ] }");
    ConfigOrError lbConfigs = lbf.parseLoadBalancerPolicy(serviceConfig);

    final List<EquivalentAddressGroup> servers =
        Collections.singletonList(new EquivalentAddressGroup(new SocketAddress(){}));
    Helper helper = new TestHelper() {
      @Override
      public Subchannel createSubchannel(CreateSubchannelArgs args) {
        assertThat(args.getAddresses()).isEqualTo(servers);
        return new TestSubchannel(args);
      }
    };
    AutoConfiguredLoadBalancer lb = lbf.newLoadBalancer(helper);
    final AtomicBoolean shutdown = new AtomicBoolean();
    TestLoadBalancer testlb = new TestLoadBalancer() {

      @Override
      public void handleNameResolutionError(Status error) {
        // noop
      }

      @Override
      public void shutdown() {
        shutdown.set(true);
      }
    };
    lb.setDelegate(testlb);

    Status addressesAcceptanceStatus = lb.tryAcceptResolvedAddresses(
        ResolvedAddresses.newBuilder()
            .setAddresses(servers)
            .setLoadBalancingPolicyConfig(lbConfigs.getConfig())
            .build());

    assertThat(addressesAcceptanceStatus.isOk()).isTrue();
    assertThat(lb.getDelegateProvider().getClass().getName()).isEqualTo(
        "io.grpc.util.SecretRoundRobinLoadBalancerProvider$Provider");
    assertTrue(shutdown.get());
  }

  @Test
  @SuppressWarnings("unchecked")
  public void acceptResolvedAddresses_propagateLbConfigToDelegate() throws Exception {
    Map<String, ?> rawServiceConfig =
        parseConfig("{\"loadBalancingConfig\": [ {\"test_lb\": { \"setting1\": \"high\" } } ] }");
    ConfigOrError lbConfigs = lbf.parseLoadBalancerPolicy(rawServiceConfig);
    assertThat(lbConfigs.getConfig()).isNotNull();

    final List<EquivalentAddressGroup> servers =
        Collections.singletonList(new EquivalentAddressGroup(new SocketAddress(){}));
    Helper helper = new TestHelper();
    AutoConfiguredLoadBalancer lb = lbf.newLoadBalancer(helper);

    Status addressesAcceptanceStatus = lb.tryAcceptResolvedAddresses(
        ResolvedAddresses.newBuilder()
            .setAddresses(servers)
            .setLoadBalancingPolicyConfig(lbConfigs.getConfig())
            .build());

    verify(testLbBalancerProvider).newLoadBalancer(same(helper));
    assertThat(addressesAcceptanceStatus.isOk()).isTrue();
    assertThat(lb.getDelegate()).isSameInstanceAs(testLbBalancer);
    ArgumentCaptor<ResolvedAddresses> resultCaptor =
        ArgumentCaptor.forClass(ResolvedAddresses.class);
    verify(testLbBalancer).acceptResolvedAddresses(resultCaptor.capture());
    assertThat(resultCaptor.getValue().getAddresses()).containsExactlyElementsIn(servers).inOrder();
    ArgumentCaptor<Map<String, ?>> lbConfigCaptor = ArgumentCaptor.forClass(Map.class);
    verify(testLbBalancerProvider).parseLoadBalancingPolicyConfig(lbConfigCaptor.capture());
    assertThat(lbConfigCaptor.getValue()).containsExactly("setting1", "high");
    verifyNoMoreInteractions(testLbBalancer);

    rawServiceConfig =
        parseConfig("{\"loadBalancingConfig\": [ {\"test_lb\": { \"setting1\": \"low\" } } ] }");
    lbConfigs = lbf.parseLoadBalancerPolicy(rawServiceConfig);

    addressesAcceptanceStatus = lb.tryAcceptResolvedAddresses(
        ResolvedAddresses.newBuilder()
            .setAddresses(servers)
            .setLoadBalancingPolicyConfig(lbConfigs.getConfig())
            .build());

    resultCaptor =
        ArgumentCaptor.forClass(ResolvedAddresses.class);
    verify(testLbBalancer, times(2)).acceptResolvedAddresses(resultCaptor.capture());
    assertThat(addressesAcceptanceStatus.isOk()).isTrue();
    assertThat(resultCaptor.getValue().getAddresses()).containsExactlyElementsIn(servers).inOrder();
    verify(testLbBalancerProvider, times(2))
        .parseLoadBalancingPolicyConfig(lbConfigCaptor.capture());
    assertThat(lbConfigCaptor.getValue()).containsExactly("setting1", "low");
    // Service config didn't change policy, thus the delegateLb is not swapped
    verifyNoMoreInteractions(testLbBalancer);
    verify(testLbBalancerProvider).newLoadBalancer(any(Helper.class));
  }

  @Test
  public void acceptResolvedAddresses_propagateAddrsToDelegate() throws Exception {
    Map<String, ?> rawServiceConfig =
        parseConfig("{\"loadBalancingConfig\": [ {\"test_lb\": { \"setting1\": \"high\" } } ] }");
    ConfigOrError lbConfigs = lbf.parseLoadBalancerPolicy(rawServiceConfig);
    assertThat(lbConfigs.getConfig()).isNotNull();

    Helper helper = new TestHelper();
    AutoConfiguredLoadBalancer lb = lbf.newLoadBalancer(helper);
    List<EquivalentAddressGroup> servers =
        Collections.singletonList(new EquivalentAddressGroup(new InetSocketAddress(8080){}));

    Status addressesAcceptanceStatus = lb.tryAcceptResolvedAddresses(
        ResolvedAddresses.newBuilder()
            .setAddresses(servers)
            .setLoadBalancingPolicyConfig(lbConfigs.getConfig())
            .build());

    verify(testLbBalancerProvider).newLoadBalancer(same(helper));
    assertThat(addressesAcceptanceStatus.isOk()).isTrue();
    assertThat(lb.getDelegate()).isSameInstanceAs(testLbBalancer);
    ArgumentCaptor<ResolvedAddresses> resultCaptor =
        ArgumentCaptor.forClass(ResolvedAddresses.class);
    verify(testLbBalancer).acceptResolvedAddresses(resultCaptor.capture());
    assertThat(resultCaptor.getValue().getAddresses()).containsExactlyElementsIn(servers).inOrder();

    servers =
        Collections.singletonList(new EquivalentAddressGroup(new InetSocketAddress(9090){}));
    addressesAcceptanceStatus = lb.tryAcceptResolvedAddresses(
        ResolvedAddresses.newBuilder()
            .setAddresses(servers)
            .setLoadBalancingPolicyConfig(lbConfigs.getConfig())
            .build());

    assertThat(addressesAcceptanceStatus.isOk()).isTrue();
    verify(testLbBalancer, times(2)).acceptResolvedAddresses(resultCaptor.capture());
    assertThat(resultCaptor.getValue().getAddresses()).containsExactlyElementsIn(servers).inOrder();
  }

  @Test
  public void acceptResolvedAddresses_delegateDoNotAcceptEmptyAddressList_nothing()
      throws Exception {

    // The test LB will NOT accept the addresses we give them.
    when(testLbBalancer.acceptResolvedAddresses(isA(ResolvedAddresses.class))).thenReturn(
        Status.UNAVAILABLE);

    Helper helper = new TestHelper();
    AutoConfiguredLoadBalancer lb = lbf.newLoadBalancer(helper);

    Map<String, ?> serviceConfig =
        parseConfig("{\"loadBalancingConfig\": [ {\"test_lb\": { \"setting1\": \"high\" } } ] }");
    ConfigOrError lbConfig = lbf.parseLoadBalancerPolicy(serviceConfig);
    Status addressesAcceptanceStatus = lb.tryAcceptResolvedAddresses(
        ResolvedAddresses.newBuilder()
            .setAddresses(Collections.<EquivalentAddressGroup>emptyList())
            .setLoadBalancingPolicyConfig(lbConfig.getConfig())
            .build());

    assertThat(addressesAcceptanceStatus.isOk()).isFalse();
    assertThat(lb.getDelegate()).isSameInstanceAs(testLbBalancer);
  }

  @Test
  public void acceptResolvedAddresses_delegateAcceptsEmptyAddressList()
      throws Exception {
    Helper helper = new TestHelper();
    AutoConfiguredLoadBalancer lb = lbf.newLoadBalancer(helper);

    Map<String, ?> rawServiceConfig =
        parseConfig("{\"loadBalancingConfig\": [ {\"test_lb2\": { \"setting1\": \"high\" } } ] }");
    ConfigOrError lbConfigs =
        lbf.parseLoadBalancerPolicy(rawServiceConfig);
    Status addressesAcceptanceStatus = lb.tryAcceptResolvedAddresses(
        ResolvedAddresses.newBuilder()
            .setAddresses(Collections.<EquivalentAddressGroup>emptyList())
            .setLoadBalancingPolicyConfig(lbConfigs.getConfig())
            .build());

    assertThat(addressesAcceptanceStatus.isOk()).isTrue();
    assertThat(lb.getDelegate()).isSameInstanceAs(testLbBalancer2);
    ArgumentCaptor<ResolvedAddresses> resultCaptor =
        ArgumentCaptor.forClass(ResolvedAddresses.class);
    verify(testLbBalancer2).acceptResolvedAddresses(resultCaptor.capture());
    assertThat(resultCaptor.getValue().getAddresses()).isEmpty();
    assertThat(resultCaptor.getValue().getLoadBalancingPolicyConfig())
        .isEqualTo(nextParsedConfigOrError2.get().getConfig());
  }

  @Test
  public void acceptResolvedAddresses_useSelectedLbPolicy() throws Exception {
    Map<String, ?> rawServiceConfig =
        parseConfig("{\"loadBalancingConfig\": [{\"round_robin\": {}}]}");
    ConfigOrError lbConfigs = lbf.parseLoadBalancerPolicy(rawServiceConfig);
    assertThat(lbConfigs.getConfig()).isNotNull();
    assertThat(((PolicySelection) lbConfigs.getConfig()).provider.getClass().getName())
        .isEqualTo("io.grpc.util.SecretRoundRobinLoadBalancerProvider$Provider");

    final List<EquivalentAddressGroup> servers =
        Collections.singletonList(new EquivalentAddressGroup(new SocketAddress(){}));
    Helper helper = new TestHelper() {
      @Override
      public Subchannel createSubchannel(CreateSubchannelArgs args) {
        assertThat(args.getAddresses()).isEqualTo(servers);
        return new TestSubchannel(args);
      }
    };
    AutoConfiguredLoadBalancer lb = lbf.newLoadBalancer(helper);
    Status addressesAcceptanceStatus = lb.tryAcceptResolvedAddresses(
        ResolvedAddresses.newBuilder()
            .setAddresses(servers)
            .setLoadBalancingPolicyConfig(lbConfigs.getConfig())
            .build());
    assertThat(addressesAcceptanceStatus.isOk()).isTrue();
    assertThat(lb.getDelegate().getClass().getName())
        .isEqualTo("io.grpc.util.RoundRobinLoadBalancer");
  }

  @Test
  public void acceptResolvedAddresses_noLbPolicySelected_defaultToPickFirst() {
    final List<EquivalentAddressGroup> servers =
        Collections.singletonList(new EquivalentAddressGroup(new SocketAddress(){}));
    Helper helper = new TestHelper() {
      @Override
      public Subchannel createSubchannel(CreateSubchannelArgs args) {
        assertThat(args.getAddresses()).isEqualTo(servers);
        return new TestSubchannel(args);
      }
    };
    AutoConfiguredLoadBalancer lb = lbf.newLoadBalancer(helper);
    Status addressesAcceptanceStatus = lb.tryAcceptResolvedAddresses(
        ResolvedAddresses.newBuilder()
            .setAddresses(servers)
            .setLoadBalancingPolicyConfig(null)
            .build());
    assertThat(addressesAcceptanceStatus.isOk()).isTrue();
    assertThat(lb.getDelegate()).isInstanceOf(pfLbClass);
  }

  @Test
  public void acceptResolvedAddresses_noLbPolicySelected_defaultToCustomDefault() {
    AutoConfiguredLoadBalancer lb = new AutoConfiguredLoadBalancerFactory("test_lb")
        .newLoadBalancer(new TestHelper());
    List<EquivalentAddressGroup> servers =
        Collections.singletonList(new EquivalentAddressGroup(new SocketAddress(){}));
    Status addressesAcceptanceStatus = lb.tryAcceptResolvedAddresses(
        ResolvedAddresses.newBuilder()
            .setAddresses(servers)
            .setLoadBalancingPolicyConfig(null)
            .build());
    assertThat(addressesAcceptanceStatus.isOk()).isTrue();
    assertThat(lb.getDelegate()).isSameInstanceAs(testLbBalancer);
  }

  @Test
  public void channelTracing_lbPolicyChanged() throws Exception {
    List<EquivalentAddressGroup> servers =
        Collections.singletonList(new EquivalentAddressGroup(new SocketAddress(){}));
    Helper helper = new TestHelper() {
      @Override
      public Subchannel createSubchannel(CreateSubchannelArgs args) {
        return new TestSubchannel(args);
      }
    };

    AutoConfiguredLoadBalancer lb =
        new AutoConfiguredLoadBalancerFactory(GrpcUtil.DEFAULT_LB_POLICY).newLoadBalancer(helper);
    Status addressesAcceptanceStatus = lb.tryAcceptResolvedAddresses(
        ResolvedAddresses.newBuilder()
            .setAddresses(servers)
            .setAttributes(Attributes.EMPTY)
            .build());

    assertThat(addressesAcceptanceStatus.isOk()).isTrue();
    verifyNoMoreInteractions(channelLogger);

    ConfigOrError testLbParsedConfig = ConfigOrError.fromConfig("foo");
    nextParsedConfigOrError.set(testLbParsedConfig);
    Map<String, ?> serviceConfig =
        parseConfig("{\"loadBalancingConfig\": [ {\"test_lb\": { } } ] }");
    ConfigOrError lbConfigs = lbf.parseLoadBalancerPolicy(serviceConfig);
    addressesAcceptanceStatus = lb.tryAcceptResolvedAddresses(
        ResolvedAddresses.newBuilder()
            .setAddresses(servers)
            .setLoadBalancingPolicyConfig(lbConfigs.getConfig())
            .build());

    assertThat(addressesAcceptanceStatus.isOk()).isTrue();
    verify(channelLogger).log(
        eq(ChannelLogLevel.INFO),
        eq("Load balancer changed from {0} to {1}"),
        eq(pfLbClass.getSimpleName()),
        eq(testLbBalancer.getClass().getSimpleName()));

    verify(channelLogger).log(
        eq(ChannelLogLevel.DEBUG),
        eq("Load-balancing config: {0}"),
        eq(testLbParsedConfig.getConfig()));
    verifyNoMoreInteractions(channelLogger);

    testLbParsedConfig = ConfigOrError.fromConfig("bar");
    nextParsedConfigOrError.set(testLbParsedConfig);
    serviceConfig = parseConfig("{\"loadBalancingConfig\": [ {\"test_lb\": { } } ] }");
    lbConfigs = lbf.parseLoadBalancerPolicy(serviceConfig);
    addressesAcceptanceStatus = lb.tryAcceptResolvedAddresses(
        ResolvedAddresses.newBuilder()
            .setAddresses(servers)
            .setLoadBalancingPolicyConfig(lbConfigs.getConfig())
            .build());
    assertThat(addressesAcceptanceStatus.isOk()).isTrue();
    verify(channelLogger).log(
        eq(ChannelLogLevel.DEBUG),
        eq("Load-balancing config: {0}"),
        eq(testLbParsedConfig.getConfig()));
    verifyNoMoreInteractions(channelLogger);
  }

  @Test
  public void parseLoadBalancerConfig_failedOnUnknown() throws Exception {
    Map<String, ?> serviceConfig =
        parseConfig("{\"loadBalancingConfig\": [ {\"magic_balancer\": {} } ] }");
    ConfigOrError parsed = lbf.parseLoadBalancerPolicy(serviceConfig);
    assertThat(parsed.getError()).isNotNull();
    assertThat(parsed.getError().getDescription())
        .isEqualTo("None of [magic_balancer] specified by Service Config are available.");
  }

  @Test
  public void parseLoadBalancerPolicy_failedOnUnknown() throws Exception {
    Map<String, ?> serviceConfig =
        parseConfig("{\"loadBalancingPolicy\": \"magic_balancer\"}");
    ConfigOrError parsed = lbf.parseLoadBalancerPolicy(serviceConfig);
    assertThat(parsed.getError()).isNotNull();
    assertThat(parsed.getError().getDescription())
        .isEqualTo("None of [magic_balancer] specified by Service Config are available.");
  }

  @Test
  public void parseLoadBalancerConfig_multipleValidPolicies() throws Exception {
    Map<String, ?> serviceConfig =
        parseConfig(
            "{\"loadBalancingConfig\": ["
                + "{\"round_robin\": {}},"
                + "{\"test_lb\": {} } ] }");
    ConfigOrError parsed = lbf.parseLoadBalancerPolicy(serviceConfig);
    assertThat(parsed).isNotNull();
    assertThat(parsed.getError()).isNull();
    assertThat(parsed.getConfig()).isInstanceOf(PolicySelection.class);
    assertThat(((PolicySelection) parsed.getConfig()).provider.getClass().getName())
        .isEqualTo("io.grpc.util.SecretRoundRobinLoadBalancerProvider$Provider");
  }

  @Test
  public void parseLoadBalancerConfig_policyShouldBeIgnoredIfConfigExists() throws Exception {
    Map<String, ?> serviceConfig =
        parseConfig(
            "{\"loadBalancingConfig\": [{\"round_robin\": {} } ],"
                + "\"loadBalancingPolicy\": \"pick_first\" }");
    ConfigOrError parsed = lbf.parseLoadBalancerPolicy(serviceConfig);
    assertThat(parsed).isNotNull();
    assertThat(parsed.getError()).isNull();
    assertThat(parsed.getConfig()).isInstanceOf(PolicySelection.class);
    assertThat(((PolicySelection) parsed.getConfig()).provider.getClass().getName())
        .isEqualTo("io.grpc.util.SecretRoundRobinLoadBalancerProvider$Provider");
  }

  @Test
  public void parseLoadBalancerConfig_policyShouldBeIgnoredEvenIfUnknownPolicyExists()
      throws Exception {
    Map<String, ?> serviceConfig =
        parseConfig(
            "{\"loadBalancingConfig\": [{\"magic_balancer\": {} } ],"
                + "\"loadBalancingPolicy\": \"round_robin\" }");
    ConfigOrError parsed = lbf.parseLoadBalancerPolicy(serviceConfig);
    assertThat(parsed.getError()).isNotNull();
    assertThat(parsed.getError().getDescription())
        .isEqualTo("None of [magic_balancer] specified by Service Config are available.");
  }

  @Test
  @SuppressWarnings("unchecked")
  public void parseLoadBalancerConfig_firstInvalidPolicy() throws Exception {
    when(testLbBalancerProvider.parseLoadBalancingPolicyConfig(any(Map.class)))
        .thenReturn(ConfigOrError.fromError(Status.UNKNOWN));
    Map<String, ?> serviceConfig =
        parseConfig(
            "{\"loadBalancingConfig\": ["
                + "{\"test_lb\": {}},"
                + "{\"round_robin\": {} } ] }");
    ConfigOrError parsed = lbf.parseLoadBalancerPolicy(serviceConfig);
    assertThat(parsed).isNotNull();
    assertThat(parsed.getConfig()).isNull();
    assertThat(parsed.getError()).isEqualTo(Status.UNKNOWN);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void parseLoadBalancerConfig_firstValidSecondInvalidPolicy() throws Exception {
    when(testLbBalancerProvider.parseLoadBalancingPolicyConfig(any(Map.class)))
        .thenReturn(ConfigOrError.fromError(Status.UNKNOWN));
    Map<String, ?> serviceConfig =
        parseConfig(
            "{\"loadBalancingConfig\": ["
                + "{\"round_robin\": {}},"
                + "{\"test_lb\": {} } ] }");
    ConfigOrError parsed = lbf.parseLoadBalancerPolicy(serviceConfig);
    assertThat(parsed).isNotNull();
    assertThat(parsed.getConfig()).isNotNull();
    assertThat(((PolicySelection) parsed.getConfig()).config).isNotNull();
  }

  @Test
  public void parseLoadBalancerConfig_someProvidesAreNotAvailable() throws Exception {
    Map<String, ?> serviceConfig =
        parseConfig("{\"loadBalancingConfig\": [ "
            + "{\"magic_balancer\": {} },"
            + "{\"round_robin\": {}} ] }");
    ConfigOrError parsed = lbf.parseLoadBalancerPolicy(serviceConfig);
    assertThat(parsed).isNotNull();
    assertThat(parsed.getConfig()).isNotNull();
    assertThat(((PolicySelection) parsed.getConfig()).config).isNotNull();
  }

  @Test
  public void parseLoadBalancerConfig_lbConfigPropagated() throws Exception {
    Map<String, ?> rawServiceConfig =
        parseConfig(
            "{\"loadBalancingConfig\": ["
                + "{\"pick_first\": {\"shuffleAddressList\": true } }"
                + "] }");
    ConfigOrError parsed = lbf.parseLoadBalancerPolicy(rawServiceConfig);
    assertThat(parsed).isNotNull();
    assertThat(parsed.getConfig()).isNotNull();
    PolicySelection policySelection = (PolicySelection) parsed.getConfig();
    assertThat(policySelection.provider).isInstanceOf(PickFirstLoadBalancerProvider.class);
    if (PickFirstLoadBalancerProvider.isEnabledNewPickFirst()) {
      assertThat(policySelection.config).isInstanceOf(PickFirstLeafLoadBalancerConfig.class);
      assertThat(((PickFirstLeafLoadBalancerConfig) policySelection.config).shuffleAddressList)
          .isTrue();
    } else {
      assertThat(policySelection.config).isInstanceOf(PickFirstLoadBalancerConfig.class);
      assertThat(((PickFirstLoadBalancerConfig) policySelection.config).shuffleAddressList)
          .isTrue();
    }
    verifyNoInteractions(channelLogger);
  }

  public static class ForwardingLoadBalancer extends LoadBalancer {
    private final LoadBalancer delegate;

    public ForwardingLoadBalancer(LoadBalancer delegate) {
      this.delegate = delegate;
    }

    protected LoadBalancer delegate() {
      return delegate;
    }

    @Override
    @Deprecated
    public void handleResolvedAddresses(ResolvedAddresses resolvedAddresses) {
      delegate().acceptResolvedAddresses(resolvedAddresses);
    }

    @Override
    public Status acceptResolvedAddresses(ResolvedAddresses resolvedAddresses) {
      return delegate().acceptResolvedAddresses(resolvedAddresses);
    }

    @Override
    public void handleNameResolutionError(Status error) {
      delegate().handleNameResolutionError(error);
    }

    @Override
    public void shutdown() {
      delegate().shutdown();
    }
  }

  @SuppressWarnings("unchecked")
  private static Map<String, ?> parseConfig(String json) throws Exception {
    return (Map<String, ?>) JsonParser.parse(json);
  }

  private static class TestLoadBalancer extends ForwardingLoadBalancer {
    TestLoadBalancer() {
      super(null);
    }
  }

  private class TestHelper extends ForwardingLoadBalancerHelper {
    final SynchronizationContext syncContext = new SynchronizationContext(
        new Thread.UncaughtExceptionHandler() {
          @Override
          public void uncaughtException(Thread t, Throwable e) {
            throw new AssertionError(e);
          }
        });

    final FakeClock fakeClock = new FakeClock();

    @Override
    protected Helper delegate() {
      return null;
    }

    @Override
    public ChannelLogger getChannelLogger() {
      return channelLogger;
    }

    @Override
    public void updateBalancingState(ConnectivityState newState, SubchannelPicker newPicker) {
      // noop
    }

    @Override
    public SynchronizationContext getSynchronizationContext() {
      return syncContext;
    }

    @Override
    public ScheduledExecutorService getScheduledExecutorService() {
      return fakeClock.getScheduledExecutorService();
    }
  }

  private static class TestSubchannel extends Subchannel {
    TestSubchannel(CreateSubchannelArgs args) {
      this.addrs = args.getAddresses();
      this.attrs = args.getAttributes();
    }

    List<EquivalentAddressGroup> addrs;
    final Attributes attrs;

    @Override
    public void start(SubchannelStateListener listener) {
    }

    @Override
    public void shutdown() {
    }

    @Override
    public void requestConnection() {
    }

    @Override
    public List<EquivalentAddressGroup> getAllAddresses() {
      return addrs;
    }

    @Override
    public Attributes getAttributes() {
      return attrs;
    }

    @Override
    public void updateAddresses(List<EquivalentAddressGroup> addrs) {
      Preconditions.checkNotNull(addrs, "addrs");
      this.addrs = addrs;
    }
  }

  private static class FakeLoadBalancerProvider extends LoadBalancerProvider {
    private final String policyName;
    private final LoadBalancer balancer;
    private final AtomicReference<ConfigOrError> nextParsedLbPolicyConfig;

    FakeLoadBalancerProvider(
        String policyName,
        LoadBalancer balancer,
        AtomicReference<ConfigOrError> nextParsedLbPolicyConfig) {
      this.policyName = policyName;
      this.balancer = balancer;
      this.nextParsedLbPolicyConfig = nextParsedLbPolicyConfig;
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
    public String getPolicyName() {
      return policyName;
    }

    @Override
    public LoadBalancer newLoadBalancer(Helper helper) {
      return balancer;
    }

    @Override
    public ConfigOrError parseLoadBalancingPolicyConfig(
        Map<String, ?> rawLoadBalancingPolicyConfig) {
      if (nextParsedLbPolicyConfig == null) {
        return super.parseLoadBalancingPolicyConfig(rawLoadBalancingPolicyConfig);
      }
      return nextParsedLbPolicyConfig.get();
    }
  }
}
