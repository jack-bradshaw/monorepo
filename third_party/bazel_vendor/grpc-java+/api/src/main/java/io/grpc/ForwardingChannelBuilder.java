/*
 * Copyright 2017 The gRPC Authors
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

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

/**
 * A {@link ManagedChannelBuilder} that delegates all its builder methods to another builder by
 * default.
 *
 * <p>DEPRECATED: Use {@link ForwardingChannelBuilder2} instead!
 *
 * <p>This class mistakenly used {@code <T extends ForwardingChannelBuilder<T>>} which causes
 * return types to be {@link ForwardingChannelBuilder} instead of {@link ManagedChannelBuilder}.
 * This pollutes the ABI of its subclasses with undesired method signatures.
 * {@link ForwardingChannelBuilder2} generates correct return types; use it instead.
 *
 * @param <T> The type of the subclass extending this abstract class.
 * @since 1.7.0
 */
public abstract class ForwardingChannelBuilder<T extends ForwardingChannelBuilder<T>>
    extends ForwardingChannelBuilder2<T> {
  /**
   * The default constructor.
   */
  protected ForwardingChannelBuilder() {
  }


  /**
   * Returns the delegated {@code ManagedChannelBuilder}.
   *
   * <p>NOTE: this method is marked deprecated instead the class itself, so that classes extending
   * {@link ForwardingChannelBuilder2} won't need class-level
   * {@code @SuppressWarnings("deprecation")} annotation. Such annotation would suppress all
   * deprecation warnings in all methods, inadvertently hiding any real deprecation warnings needing
   * to be addressed. However, each child class is expected to implement {@code delegate()}.
   * Therefore, the {@code @Deprecated} annotation is added to this method, and not to the class.
   *
   * @deprecated As of 1.60.0, use {@link ForwardingChannelBuilder2} instead.
   */
  @Override
  @Deprecated
  protected abstract ManagedChannelBuilder<?> delegate();

  @Override
  public T directExecutor() {
    delegate().directExecutor();
    return thisT();
  }

  @Override
  public T executor(Executor executor) {
    delegate().executor(executor);
    return thisT();
  }

  @Override
  public T offloadExecutor(Executor executor) {
    delegate().offloadExecutor(executor);
    return thisT();
  }

  @Override
  public T intercept(List<ClientInterceptor> interceptors) {
    delegate().intercept(interceptors);
    return thisT();
  }

  @Override
  public T intercept(ClientInterceptor... interceptors) {
    delegate().intercept(interceptors);
    return thisT();
  }

  @Override
  public T userAgent(String userAgent) {
    delegate().userAgent(userAgent);
    return thisT();
  }

  @Override
  public T overrideAuthority(String authority) {
    delegate().overrideAuthority(authority);
    return thisT();
  }

  @Override
  public T usePlaintext() {
    delegate().usePlaintext();
    return thisT();
  }

  @Override
  public T useTransportSecurity() {
    delegate().useTransportSecurity();
    return thisT();
  }

  @Deprecated
  @Override
  public T nameResolverFactory(NameResolver.Factory resolverFactory) {
    delegate().nameResolverFactory(resolverFactory);
    return thisT();
  }

  @Override
  public T defaultLoadBalancingPolicy(String policy) {
    delegate().defaultLoadBalancingPolicy(policy);
    return thisT();
  }

  @Override
  public T decompressorRegistry(DecompressorRegistry registry) {
    delegate().decompressorRegistry(registry);
    return thisT();
  }

  @Override
  public T compressorRegistry(CompressorRegistry registry) {
    delegate().compressorRegistry(registry);
    return thisT();
  }

  @Override
  public T idleTimeout(long value, TimeUnit unit) {
    delegate().idleTimeout(value, unit);
    return thisT();
  }

  @Override
  public T maxInboundMessageSize(int max) {
    delegate().maxInboundMessageSize(max);
    return thisT();
  }

  @Override
  public T maxInboundMetadataSize(int max) {
    delegate().maxInboundMetadataSize(max);
    return thisT();
  }

  @Override
  public T keepAliveTime(long keepAliveTime, TimeUnit timeUnit) {
    delegate().keepAliveTime(keepAliveTime, timeUnit);
    return thisT();
  }

  @Override
  public T keepAliveTimeout(long keepAliveTimeout, TimeUnit timeUnit) {
    delegate().keepAliveTimeout(keepAliveTimeout, timeUnit);
    return thisT();
  }

  @Override
  public T keepAliveWithoutCalls(boolean enable) {
    delegate().keepAliveWithoutCalls(enable);
    return thisT();
  }

  @Override
  public T maxRetryAttempts(int maxRetryAttempts) {
    delegate().maxRetryAttempts(maxRetryAttempts);
    return thisT();
  }

  @Override
  public T maxHedgedAttempts(int maxHedgedAttempts) {
    delegate().maxHedgedAttempts(maxHedgedAttempts);
    return thisT();
  }

  @Override
  public T retryBufferSize(long bytes) {
    delegate().retryBufferSize(bytes);
    return thisT();
  }

  @Override
  public T perRpcBufferLimit(long bytes) {
    delegate().perRpcBufferLimit(bytes);
    return thisT();
  }

  @Override
  public T disableRetry() {
    delegate().disableRetry();
    return thisT();
  }

  @Override
  public T enableRetry() {
    delegate().enableRetry();
    return thisT();
  }

  @Override
  public T setBinaryLog(BinaryLog binaryLog) {
    delegate().setBinaryLog(binaryLog);
    return thisT();
  }

  @Override
  public T maxTraceEvents(int maxTraceEvents) {
    delegate().maxTraceEvents(maxTraceEvents);
    return thisT();
  }

  @Override
  public T proxyDetector(ProxyDetector proxyDetector) {
    delegate().proxyDetector(proxyDetector);
    return thisT();
  }

  @Override
  public T defaultServiceConfig(@Nullable Map<String, ?> serviceConfig) {
    delegate().defaultServiceConfig(serviceConfig);
    return thisT();
  }

  @Override
  public T disableServiceConfigLookUp() {
    delegate().disableServiceConfigLookUp();
    return thisT();
  }

  /**
   * Returns the correctly typed version of the builder.
   */
  private T thisT() {
    @SuppressWarnings("unchecked")
    T thisT = (T) this;
    return thisT;
  }
}
