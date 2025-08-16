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

package io.grpc.census;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.grpc.ClientStreamTracer.NAME_RESOLUTION_DELAYED;
import static io.grpc.census.internal.ObservabilityCensusConstants.CLIENT_TRACE_SPAN_CONTEXT_KEY;

import com.google.common.annotations.VisibleForTesting;
import io.grpc.Attributes;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ClientStreamTracer;
import io.grpc.Context;
import io.grpc.ForwardingClientCall.SimpleForwardingClientCall;
import io.grpc.ForwardingClientCallListener.SimpleForwardingClientCallListener;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.ServerStreamTracer;
import io.grpc.StreamTracer;
import io.opencensus.trace.AttributeValue;
import io.opencensus.trace.BlankSpan;
import io.opencensus.trace.EndSpanOptions;
import io.opencensus.trace.MessageEvent;
import io.opencensus.trace.Span;
import io.opencensus.trace.SpanContext;
import io.opencensus.trace.Status;
import io.opencensus.trace.Tracer;
import io.opencensus.trace.propagation.BinaryFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

/**
 * Provides factories for {@link StreamTracer} that records traces to Census.
 *
 * <p>On the client-side, a factory is created for each call, because ClientCall starts earlier than
 * the ClientStream, and in some cases may even not create a ClientStream at all.  Therefore, it's
 * the factory that reports the summary to Census.
 *
 * <p>On the server-side, there is only one ServerStream per each ServerCall, and ServerStream
 * starts earlier than the ServerCall.  Therefore, only one tracer is created per stream/call and
 * it's the tracer that reports the summary to Census.
 */
final class CensusTracingModule {
  private static final Logger logger = Logger.getLogger(CensusTracingModule.class.getName());

  @Nullable
  private static final AtomicIntegerFieldUpdater<CallAttemptsTracerFactory> callEndedUpdater;

  @Nullable private static final AtomicIntegerFieldUpdater<ServerTracer> streamClosedUpdater;

  /*
   * When using Atomic*FieldUpdater, some Samsung Android 5.0.x devices encounter a bug in their JDK
   * reflection API that triggers a NoSuchFieldException. When this occurs, we fallback to
   * (potentially racy) direct updates of the volatile variables.
   */
  static {
    AtomicIntegerFieldUpdater<CallAttemptsTracerFactory> tmpCallEndedUpdater;
    AtomicIntegerFieldUpdater<ServerTracer> tmpStreamClosedUpdater;
    try {
      tmpCallEndedUpdater =
          AtomicIntegerFieldUpdater.newUpdater(CallAttemptsTracerFactory.class, "callEnded");
      tmpStreamClosedUpdater =
          AtomicIntegerFieldUpdater.newUpdater(ServerTracer.class, "streamClosed");
    } catch (Throwable t) {
      logger.log(Level.SEVERE, "Creating atomic field updaters failed", t);
      tmpCallEndedUpdater = null;
      tmpStreamClosedUpdater = null;
    }
    callEndedUpdater = tmpCallEndedUpdater;
    streamClosedUpdater = tmpStreamClosedUpdater;
  }

  private final Tracer censusTracer;
  @VisibleForTesting
  final Metadata.Key<SpanContext> tracingHeader;
  private final TracingClientInterceptor clientInterceptor = new TracingClientInterceptor();
  private final ServerTracerFactory serverTracerFactory = new ServerTracerFactory();

  CensusTracingModule(
      Tracer censusTracer, final BinaryFormat censusPropagationBinaryFormat) {
    this.censusTracer = checkNotNull(censusTracer, "censusTracer");
    checkNotNull(censusPropagationBinaryFormat, "censusPropagationBinaryFormat");
    this.tracingHeader =
        Metadata.Key.of("grpc-trace-bin", new Metadata.BinaryMarshaller<SpanContext>() {
            @Override
            public byte[] toBytes(SpanContext context) {
              return censusPropagationBinaryFormat.toByteArray(context);
            }

            @Override
            public SpanContext parseBytes(byte[] serialized) {
              try {
                return censusPropagationBinaryFormat.fromByteArray(serialized);
              } catch (Exception e) {
                logger.log(Level.FINE, "Failed to parse tracing header", e);
                return SpanContext.INVALID;
              }
            }
          });
  }

  /**
   * Creates a {@link CallAttemptsTracerFactory} for a new call.
   */
  @VisibleForTesting
  CallAttemptsTracerFactory newClientCallTracer(
      @Nullable Span clientSpan, MethodDescriptor<?, ?> method) {
    return new CallAttemptsTracerFactory(clientSpan, method);
  }

  /**
   * Returns the server tracer factory.
   */
  ServerStreamTracer.Factory getServerTracerFactory() {
    return serverTracerFactory;
  }

  /**
   * Returns the client interceptor that facilitates Census-based stats reporting.
   */
  ClientInterceptor getClientInterceptor() {
    return clientInterceptor;
  }

  @VisibleForTesting
  static Status convertStatus(io.grpc.Status grpcStatus) {
    Status status;
    switch (grpcStatus.getCode()) {
      case OK:
        status = Status.OK;
        break;
      case CANCELLED:
        status = Status.CANCELLED;
        break;
      case UNKNOWN:
        status = Status.UNKNOWN;
        break;
      case INVALID_ARGUMENT:
        status = Status.INVALID_ARGUMENT;
        break;
      case DEADLINE_EXCEEDED:
        status = Status.DEADLINE_EXCEEDED;
        break;
      case NOT_FOUND:
        status = Status.NOT_FOUND;
        break;
      case ALREADY_EXISTS:
        status = Status.ALREADY_EXISTS;
        break;
      case PERMISSION_DENIED:
        status = Status.PERMISSION_DENIED;
        break;
      case RESOURCE_EXHAUSTED:
        status = Status.RESOURCE_EXHAUSTED;
        break;
      case FAILED_PRECONDITION:
        status = Status.FAILED_PRECONDITION;
        break;
      case ABORTED:
        status = Status.ABORTED;
        break;
      case OUT_OF_RANGE:
        status = Status.OUT_OF_RANGE;
        break;
      case UNIMPLEMENTED:
        status = Status.UNIMPLEMENTED;
        break;
      case INTERNAL:
        status = Status.INTERNAL;
        break;
      case UNAVAILABLE:
        status = Status.UNAVAILABLE;
        break;
      case DATA_LOSS:
        status = Status.DATA_LOSS;
        break;
      case UNAUTHENTICATED:
        status = Status.UNAUTHENTICATED;
        break;
      default:
        throw new AssertionError("Unhandled status code " + grpcStatus.getCode());
    }
    if (grpcStatus.getDescription() != null) {
      status = status.withDescription(grpcStatus.getDescription());
    }
    return status;
  }

  private static EndSpanOptions createEndSpanOptions(
      io.grpc.Status status, boolean sampledToLocalTracing) {
    return EndSpanOptions.builder()
        .setStatus(convertStatus(status))
        .setSampleToLocalSpanStore(sampledToLocalTracing)
        .build();
  }

  private void recordMessageEvent(
      Span span, MessageEvent.Type type,
      int seqNo, long optionalWireSize, long optionalUncompressedSize) {
    MessageEvent.Builder eventBuilder = MessageEvent.builder(type, seqNo);
    if (optionalUncompressedSize != -1) {
      eventBuilder.setUncompressedMessageSize(optionalUncompressedSize);
    }
    if (optionalWireSize != -1) {
      eventBuilder.setCompressedMessageSize(optionalWireSize);
    }
    span.addMessageEvent(eventBuilder.build());
  }

  private void recordAnnotation(
      Span span, MessageEvent.Type type, int seqNo, boolean isCompressed, long size) {
    String messageType = isCompressed ? "compressed" : "uncompressed";
    Map<String, AttributeValue> attributes = new HashMap<>();
    attributes.put("id", AttributeValue.longAttributeValue(seqNo));
    attributes.put("type", AttributeValue.stringAttributeValue(messageType));

    String messageDirection = type == MessageEvent.Type.SENT ? "↗ " : "↘ ";
    String inlineDescription =
        messageDirection + size + " bytes " + type.name().toLowerCase(Locale.US);
    span.addAnnotation(inlineDescription, attributes);
  }

  @VisibleForTesting
  final class CallAttemptsTracerFactory extends ClientStreamTracer.Factory {
    volatile int callEnded;

    private final boolean isSampledToLocalTracing;
    private final Span span;
    private final String fullMethodName;

    CallAttemptsTracerFactory(@Nullable Span clientSpan, MethodDescriptor<?, ?> method) {
      checkNotNull(method, "method");
      this.isSampledToLocalTracing = method.isSampledToLocalTracing();
      this.fullMethodName = method.getFullMethodName();
      this.span = clientSpan;
    }

    @Override
    public ClientStreamTracer newClientStreamTracer(
        ClientStreamTracer.StreamInfo info, Metadata headers) {
      Span attemptSpan = censusTracer
          .spanBuilderWithExplicitParent(
              "Attempt." + fullMethodName.replace('/', '.'),
              span)
          .setRecordEvents(true)
          .startSpan();
      attemptSpan.putAttribute(
          "previous-rpc-attempts", AttributeValue.longAttributeValue(info.getPreviousAttempts()));
      attemptSpan.putAttribute(
          "transparent-retry", AttributeValue.booleanAttributeValue(info.isTransparentRetry()));
      if (info.getCallOptions().getOption(NAME_RESOLUTION_DELAYED) != null) {
        span.addAnnotation("Delayed name resolution complete");
      }
      return new ClientTracer(attemptSpan, span, tracingHeader, isSampledToLocalTracing);
    }

    /**
     * Record a finished call and mark the current time as the end time.
     *
     * <p>Can be called from any thread without synchronization.  Calling it the second time or more
     * is a no-op.
     */
    void callEnded(io.grpc.Status status) {
      if (callEndedUpdater != null) {
        if (callEndedUpdater.getAndSet(this, 1) != 0) {
          return;
        }
      } else {
        if (callEnded != 0) {
          return;
        }
        callEnded = 1;
      }
      span.end(createEndSpanOptions(status, isSampledToLocalTracing));
    }
  }

  private final class ClientTracer extends ClientStreamTracer {
    private final Span span;
    private final Span parentSpan;
    final Metadata.Key<SpanContext> tracingHeader;
    final boolean isSampledToLocalTracing;
    volatile int seqNo;
    boolean isPendingStream;

    ClientTracer(
        Span span, Span parentSpan, Metadata.Key<SpanContext> tracingHeader,
        boolean isSampledToLocalTracing) {
      this.span = checkNotNull(span, "span");
      this.parentSpan = checkNotNull(parentSpan, "parent span");
      this.tracingHeader = tracingHeader;
      this.isSampledToLocalTracing = isSampledToLocalTracing;
    }

    @Override
    public void streamCreated(Attributes transportAtts, Metadata headers) {
      if (span != BlankSpan.INSTANCE) {
        headers.discardAll(tracingHeader);
        headers.put(tracingHeader, span.getContext());
      }
      if (isPendingStream) {
        span.addAnnotation("Delayed LB pick complete");
      }
    }

    @Override
    public void createPendingStream() {
      isPendingStream = true;
    }

    @Override
    public void outboundMessageSent(
        int seqNo, long optionalWireSize, long optionalUncompressedSize) {
      recordMessageEvent(
          span, MessageEvent.Type.SENT, seqNo, optionalWireSize, optionalUncompressedSize);
    }

    @Override
    public void inboundMessageRead(
        int seqNo, long optionalWireSize, long optionalUncompressedSize) {
      recordAnnotation(
          span, MessageEvent.Type.RECEIVED, seqNo, true, optionalWireSize);
    }

    @Override
    public void inboundMessage(int seqNo) {
      this.seqNo = seqNo;
    }

    @Override
    public void inboundUncompressedSize(long bytes) {
      recordAnnotation(
          parentSpan, MessageEvent.Type.RECEIVED, seqNo, false, bytes);
    }

    @Override
    public void streamClosed(io.grpc.Status status) {
      span.end(createEndSpanOptions(status, isSampledToLocalTracing));
    }
  }


  private final class ServerTracer extends ServerStreamTracer {
    private final Span span;
    volatile boolean isSampledToLocalTracing;
    volatile int streamClosed;
    private int seqNo;

    ServerTracer(String fullMethodName, @Nullable SpanContext remoteSpan) {
      checkNotNull(fullMethodName, "fullMethodName");
      this.span =
          censusTracer
              .spanBuilderWithRemoteParent(
                  generateTraceSpanName(true, fullMethodName),
                  remoteSpan)
              .setRecordEvents(true)
              .startSpan();
    }

    @Override
    public void serverCallStarted(ServerCallInfo<?, ?> callInfo) {
      isSampledToLocalTracing = callInfo.getMethodDescriptor().isSampledToLocalTracing();
    }

    /**
     * Record a finished stream and mark the current time as the end time.
     *
     * <p>Can be called from any thread without synchronization.  Calling it the second time or more
     * is a no-op.
     */
    @Override
    public void streamClosed(io.grpc.Status status) {
      if (streamClosedUpdater != null) {
        if (streamClosedUpdater.getAndSet(this, 1) != 0) {
          return;
        }
      } else {
        if (streamClosed != 0) {
          return;
        }
        streamClosed = 1;
      }
      span.end(createEndSpanOptions(status, isSampledToLocalTracing));
    }

    /*
     TODO(dnvindhya): Replace deprecated ContextUtils usage with ContextHandleUtils to interact
     with io.grpc.Context as described in {@link io.opencensus.trace.unsafeContextUtils} to remove
     SuppressWarnings annotation.
    */
    @SuppressWarnings("deprecation")
    @Override
    public Context filterContext(Context context) {
      // Access directly the unsafe trace API to create the new Context. This is a safe usage
      // because gRPC always creates a new Context for each of the server calls and does not
      // inherit from the parent Context.
      return io.opencensus.trace.unsafe.ContextUtils.withValue(context, span);
    }

    @Override
    public void outboundMessageSent(
        int seqNo, long optionalWireSize, long optionalUncompressedSize) {
      recordMessageEvent(
          span, MessageEvent.Type.SENT, seqNo, optionalWireSize, optionalUncompressedSize);
    }

    @Override
    public void inboundMessageRead(
        int seqNo, long optionalWireSize, long optionalUncompressedSize) {
      recordAnnotation(
          span, MessageEvent.Type.RECEIVED, seqNo, true, optionalWireSize);
    }

    @Override
    public void inboundMessage(int seqNo) {
      this.seqNo = seqNo;
    }

    @Override
    public void inboundUncompressedSize(long bytes) {
      recordAnnotation(
          span, MessageEvent.Type.RECEIVED, seqNo, false, bytes);
    }
  }

  @VisibleForTesting
  final class ServerTracerFactory extends ServerStreamTracer.Factory {
    @SuppressWarnings("ReferenceEquality")
    @Override
    public ServerStreamTracer newServerStreamTracer(String fullMethodName, Metadata headers) {
      SpanContext remoteSpan = headers.get(tracingHeader);
      if (remoteSpan == SpanContext.INVALID) {
        remoteSpan = null;
      }
      return new ServerTracer(fullMethodName, remoteSpan);
    }
  }

  @VisibleForTesting
  final class TracingClientInterceptor implements ClientInterceptor {

    @SuppressWarnings("deprecation")
    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
        MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
      // New RPCs on client-side inherit the tracing context from the current Context.
      // Safe usage of the unsafe trace API because CONTEXT_SPAN_KEY.get() returns the same value
      // as Tracer.getCurrentSpan() except when no value available when the return value is null
      // for the direct access and BlankSpan when Tracer API is used.
      Span parentSpan = io.opencensus.trace.unsafe.ContextUtils.getValue(Context.current());
      Span clientSpan = censusTracer
          .spanBuilderWithExplicitParent(
              generateTraceSpanName(false, method.getFullMethodName()),
              parentSpan)
          .setRecordEvents(true)
          .startSpan();

      final CallAttemptsTracerFactory tracerFactory = newClientCallTracer(clientSpan, method);
      ClientCall<ReqT, RespT> call =
          next.newCall(
              method,
              callOptions.withStreamTracerFactory(tracerFactory)
                  .withOption(CLIENT_TRACE_SPAN_CONTEXT_KEY, clientSpan.getContext()));
      return new SimpleForwardingClientCall<ReqT, RespT>(call) {
        @Override
        public void start(Listener<RespT> responseListener, Metadata headers) {
          delegate().start(
              new SimpleForwardingClientCallListener<RespT>(responseListener) {
                @Override
                public void onClose(io.grpc.Status status, Metadata trailers) {
                  tracerFactory.callEnded(status);
                  super.onClose(status, trailers);
                }
              },
              headers);
        }
      };
    }
  }

  /**
   * Convert a full method name to a tracing span name.
   *
   * @param isServer {@code false} if the span is on the client-side, {@code true} if on the
   *                 server-side
   * @param fullMethodName the method name as returned by
   *        {@link MethodDescriptor#getFullMethodName}.
   */
  @VisibleForTesting
  static String generateTraceSpanName(boolean isServer, String fullMethodName) {
    String prefix = isServer ? "Recv" : "Sent";
    return prefix + "." + fullMethodName.replace('/', '.');
  }

}
