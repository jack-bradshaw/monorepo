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

import com.google.common.collect.ImmutableSet;
import io.grpc.MethodDescriptor;
import io.grpc.Status.Code;
import io.grpc.testing.TestMethodDescriptors;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** Unit tests for HedgingPolicy. */
@RunWith(JUnit4.class)
public class HedgingPolicyTest {
  @Test
  public void getHedgingPolicies() throws Exception {
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new InputStreamReader(RetryPolicyTest.class.getResourceAsStream(
          "/io/grpc/internal/test_hedging_service_config.json"), "UTF-8"));
      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        sb.append(line).append('\n');
      }
      Object serviceConfigObj = JsonParser.parse(sb.toString());
      assertTrue(serviceConfigObj instanceof Map);

      @SuppressWarnings("unchecked")
      Map<String, ?> serviceConfig = (Map<String, ?>) serviceConfigObj;
      ManagedChannelServiceConfig channelServiceConfig =
          ManagedChannelServiceConfig.fromServiceConfig(
              serviceConfig,
              /* retryEnabled= */ true,
              /* maxRetryAttemptsLimit= */ 3,
              /* maxHedgedAttemptsLimit= */ 4,
              /* loadBalancingConfig= */ null);

      MethodDescriptor.Builder<Void, Void> builder = TestMethodDescriptors.voidMethod().toBuilder();

      MethodDescriptor<Void, Void> method = builder.setFullMethodName("not/exist").build();
      assertThat(channelServiceConfig.getMethodConfig(method)).isNull();

      method = builder.setFullMethodName("not_exist/Foo1").build();
      assertThat(channelServiceConfig.getMethodConfig(method)).isNull();

      method = builder.setFullMethodName("SimpleService1/not_exist").build();
      assertThat(channelServiceConfig.getMethodConfig(method).hedgingPolicy).isEqualTo(
          new HedgingPolicy(
              3,
              TimeUnit.MILLISECONDS.toNanos(2100),
              ImmutableSet.of(Code.UNAVAILABLE, Code.RESOURCE_EXHAUSTED)));

      method = builder.setFullMethodName("SimpleService1/Foo1").build();
      assertThat(channelServiceConfig.getMethodConfig(method).hedgingPolicy).isEqualTo(
          new HedgingPolicy(
              4,
              TimeUnit.MILLISECONDS.toNanos(100),
              ImmutableSet.of(Code.UNAVAILABLE)));

      method = builder.setFullMethodName("SimpleService2/not_exist").build();
      assertThat(channelServiceConfig.getMethodConfig(method).hedgingPolicy).isNull();

      method = builder.setFullMethodName("SimpleService2/Foo2").build();
      assertThat(channelServiceConfig.getMethodConfig(method).hedgingPolicy).isEqualTo(
          new HedgingPolicy(
              4,
              TimeUnit.MILLISECONDS.toNanos(100),
              ImmutableSet.of(Code.UNAVAILABLE)));
    } finally {
      if (reader != null) {
        reader.close();
      }
    }
  }

  @Test
  public void getRetryPolicies_hedgingDisabled() throws Exception {
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new InputStreamReader(RetryPolicyTest.class.getResourceAsStream(
          "/io/grpc/internal/test_hedging_service_config.json"), "UTF-8"));
      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        sb.append(line).append('\n');
      }
      Object serviceConfigObj = JsonParser.parse(sb.toString());
      assertTrue(serviceConfigObj instanceof Map);

      @SuppressWarnings("unchecked")
      Map<String, ?> serviceConfig = (Map<String, ?>) serviceConfigObj;
      ManagedChannelServiceConfig channelServiceConfig =
          ManagedChannelServiceConfig.fromServiceConfig(
              serviceConfig,
              /* retryEnabled= */ false,
              /* maxRetryAttemptsLimit= */ 3,
              /* maxHedgedAttemptsLimit= */ 4,
              /* loadBalancingConfig= */ null);
      MethodDescriptor.Builder<Void, Void> builder = TestMethodDescriptors.voidMethod().toBuilder();

      MethodDescriptor<Void, Void> method =
          builder.setFullMethodName("SimpleService1/Foo1").build();
      assertThat(channelServiceConfig.getMethodConfig(method).hedgingPolicy).isNull();
    } finally {
      if (reader != null) {
        reader.close();
      }
    }
  }
}
