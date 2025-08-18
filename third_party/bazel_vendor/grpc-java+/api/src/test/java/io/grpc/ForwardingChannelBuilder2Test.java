/*
 * Copyright 2023 The gRPC Authors
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

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import com.google.common.base.Defaults;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Unit tests for {@link ForwardingChannelBuilder2}.
 */
@RunWith(JUnit4.class)
public class ForwardingChannelBuilder2Test {
  private final ManagedChannelBuilder<?> mockDelegate = mock(ManagedChannelBuilder.class);

  private final ForwardingChannelBuilder2<?> testChannelBuilder = new TestBuilder();

  private final class TestBuilder extends ForwardingChannelBuilder2<TestBuilder> {
    @Override
    protected ManagedChannelBuilder<?> delegate() {
      return mockDelegate;
    }
  }

  @Test
  public void allMethodsForwarded() throws Exception {
    ForwardingTestUtil.testMethodsForwarded(
        ManagedChannelBuilder.class,
        mockDelegate,
        testChannelBuilder,
        Collections.emptyList(),
        new ForwardingTestUtil.ArgumentProvider() {
          @Override
          public Object get(Method method, int argPos, Class<?> clazz) {
            if (method.getName().equals("maxInboundMetadataSize")) {
              assertThat(argPos).isEqualTo(0);
              return 1; // an arbitrary positive number
            }
            return null;
          }
        });
  }

  @Test
  public void allBuilderMethodsReturnThis() throws Exception {
    for (Method method : ManagedChannelBuilder.class.getDeclaredMethods()) {
      if (Modifier.isStatic(method.getModifiers()) || Modifier.isPrivate(method.getModifiers())) {
        continue;
      }
      if (method.getName().equals("build")) {
        continue;
      }
      Class<?>[] argTypes = method.getParameterTypes();
      Object[] args = new Object[argTypes.length];
      for (int i = 0; i < argTypes.length; i++) {
        args[i] = Defaults.defaultValue(argTypes[i]);
      }
      if (method.getName().equals("maxInboundMetadataSize")) {
        args[0] = 1; // an arbitrary positive number
      }

      Object returnedValue = method.invoke(testChannelBuilder, args);

      assertThat(returnedValue).isSameInstanceAs(testChannelBuilder);
    }
  }

  @Test
  public void buildReturnsDelegateBuildByDefault() {
    ManagedChannel mockChannel = mock(ManagedChannel.class);
    doReturn(mockChannel).when(mockDelegate).build();

    assertThat(testChannelBuilder.build()).isSameInstanceAs(mockChannel);
  }
}
