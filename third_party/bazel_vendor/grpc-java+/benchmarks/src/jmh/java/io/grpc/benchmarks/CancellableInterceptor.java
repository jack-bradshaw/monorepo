/*
 * Copyright 2018, gRPC Authors All rights reserved.
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

package io.grpc.benchmarks;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.MethodDescriptor;

/** Interceptor that lets you cancel the most recent call made. This class is not thread-safe. */
class CancellableInterceptor implements ClientInterceptor {
  private ClientCall<?, ?> call;

  @Override
  public <ReqT,RespT> ClientCall<ReqT,RespT> interceptCall(
      MethodDescriptor<ReqT,RespT> method, CallOptions callOptions, Channel next) {
    ClientCall<ReqT, RespT> call = next.newCall(method, callOptions);
    this.call = call;
    return call;
  }

  public void cancel(String message, Throwable cause) {
    if (call == null) {
      throw new NullPointerException("No previous call");
    }
    call.cancel(message, cause);
  }
}
