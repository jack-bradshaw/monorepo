/*
 * Copyright 2022 The gRPC Authors
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

package io.grpc.xds;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.grpc.LoadBalancer;
import io.grpc.LoadBalancer.Helper;
import io.grpc.LoadBalancerProvider;
import io.grpc.LoadBalancerRegistry;
import io.grpc.NameResolver;
import io.grpc.util.GracefulSwitchLoadBalancerAccessor;
import io.grpc.xds.WrrLocalityLoadBalancer.WrrLocalityConfig;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests for {@link WrrLocalityLoadBalancerProvider}.
 */
@RunWith(JUnit4.class)
public class WrrLocalityLoadBalancerProviderTest {

  @Test
  public void provided() {
    LoadBalancerProvider provider =
        LoadBalancerRegistry.getDefaultRegistry().getProvider(
            XdsLbPolicies.WRR_LOCALITY_POLICY_NAME);
    assertThat(provider).isInstanceOf(WrrLocalityLoadBalancerProvider.class);
  }

  @Test
  public void providesLoadBalancer()  {
    Helper helper = mock(Helper.class);
    when(helper.getAuthority()).thenReturn("api.google.com");
    LoadBalancerProvider provider = new WrrLocalityLoadBalancerProvider();
    LoadBalancer loadBalancer = provider.newLoadBalancer(helper);
    assertThat(loadBalancer).isInstanceOf(WrrLocalityLoadBalancer.class);
  }

  @Test
  public void parseConfig() {
    Map<String, ?> rawConfig = ImmutableMap.of("childPolicy",
        ImmutableList.of(ImmutableMap.of("round_robin", ImmutableMap.of())));

    WrrLocalityLoadBalancerProvider provider = new WrrLocalityLoadBalancerProvider();
    NameResolver.ConfigOrError configOrError = provider.parseLoadBalancingPolicyConfig(rawConfig);
    WrrLocalityConfig config = (WrrLocalityConfig) configOrError.getConfig();
    LoadBalancerProvider childProvider =
        GracefulSwitchLoadBalancerAccessor.getChildProvider(config.childConfig);
    assertThat(childProvider.getPolicyName()).isEqualTo("round_robin");
  }
}
