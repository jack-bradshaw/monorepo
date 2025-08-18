/*
 * Copyright 2020 The gRPC Authors
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

package io.grpc.rls;

import static com.google.common.base.Preconditions.checkNotNull;

import io.grpc.Internal;
import io.grpc.LoadBalancer;
import io.grpc.LoadBalancerProvider;
import io.grpc.NameResolver.ConfigOrError;
import io.grpc.Status;
import io.grpc.internal.JsonUtil;
import io.grpc.rls.LbPolicyConfiguration.ChildLoadBalancingPolicy;
import io.grpc.rls.RlsProtoConverters.RouteLookupConfigConverter;
import io.grpc.rls.RlsProtoData.RouteLookupConfig;
import java.util.Map;

/**
 * The provider for the "rls_experimental" balancing policy.  This class should not be directly
 * referenced in code.  The policy should be accessed through {@link
 * io.grpc.LoadBalancerRegistry#getProvider} with the name "rls_experimental".
 */
@Internal
public final class RlsLoadBalancerProvider extends LoadBalancerProvider {

  @Override
  public boolean isAvailable() {
    return true;
  }

  @Override
  public int getPriority() {
    return 6;
  }

  @Override
  public String getPolicyName() {
    return "rls_experimental";
  }

  @Override
  public LoadBalancer newLoadBalancer(LoadBalancer.Helper helper) {
    return new RlsLoadBalancer(helper);
  }

  @Override
  public ConfigOrError parseLoadBalancingPolicyConfig(Map<String, ?> rawLoadBalancingConfigPolicy) {
    try {
      RouteLookupConfig routeLookupConfig = new RouteLookupConfigConverter()
          .convert(JsonUtil.getObject(rawLoadBalancingConfigPolicy, "routeLookupConfig"));
      Map<String, ?> routeLookupChannelServiceConfig =
          JsonUtil.getObject(rawLoadBalancingConfigPolicy, "routeLookupChannelServiceConfig");
      ChildLoadBalancingPolicy lbPolicy = ChildLoadBalancingPolicy
          .create(
              JsonUtil.getString(rawLoadBalancingConfigPolicy, "childPolicyConfigTargetFieldName"),
              JsonUtil.checkObjectList(
                  checkNotNull(JsonUtil.getList(rawLoadBalancingConfigPolicy, "childPolicy"))));
      return ConfigOrError.fromConfig(
          new LbPolicyConfiguration(routeLookupConfig, routeLookupChannelServiceConfig, lbPolicy));
    } catch (Exception e) {
      return ConfigOrError.fromError(
          Status.UNAVAILABLE
              .withDescription("can't parse config: " + e.getMessage())
              .withCause(e));
    }
  }
}
