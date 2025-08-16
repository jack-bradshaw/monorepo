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

package io.grpc.android.integrationtest;

import android.util.Log;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.testing.integration.AbstractInteropTest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.Callable;
import org.junit.AssumptionViolatedException;

/**
 * Used to run a single test case against a channel in a separate thread.
 */
public class TestCallable implements Callable<String> {
  private final ManagedChannel channel;
  private final String testCase;

  private static final String LOG_TAG = "GrpcInteropTask";
  static final String SUCCESS_MESSAGE = "Success!";

  public TestCallable(ManagedChannel channel, String testCase) {
    this.channel = channel;
    this.testCase = testCase;
  }

  @Override
  public String call() {
    Tester tester = new Tester(channel);
    tester.setUp();
    try {
      runTest(tester, testCase);
      return SUCCESS_MESSAGE;
    } catch (Throwable t) {
      // Print the stack trace to logcat.
      t.printStackTrace();
      // Then print to the error message.
      StringWriter sw = new StringWriter();
      t.printStackTrace(new PrintWriter(sw));
      return "Failed... : " + t.getMessage() + "\n" + sw;
    } finally {
      try {
        tester.tearDown();
      } catch (RuntimeException ex) {
        throw ex;
      } catch (Exception ex) {
        throw new RuntimeException(ex);
      }
    }
  }

  private void runTest(Tester tester, String testCase) throws Exception {
    Log.i(LOG_TAG, "Running test case: " + testCase);
    if ("empty_unary".equals(testCase)) {
      tester.emptyUnary();
    } else if ("large_unary".equals(testCase)) {
      try {
        tester.largeUnary();
      } catch (AssumptionViolatedException e) {
        // This test case requires more memory than most Android devices have available
        Log.w(LOG_TAG, "Skipping " + testCase + " due to assumption violation", e);
      }
    } else if ("client_streaming".equals(testCase)) {
      tester.clientStreaming();
    } else if ("server_streaming".equals(testCase)) {
      tester.serverStreaming();
    } else if ("ping_pong".equals(testCase)) {
      tester.pingPong();
    } else if ("empty_stream".equals(testCase)) {
      tester.emptyStream();
    } else if ("cancel_after_begin".equals(testCase)) {
      tester.cancelAfterBegin();
    } else if ("cancel_after_first_response".equals(testCase)) {
      tester.cancelAfterFirstResponse();
    } else if ("full_duplex_call_should_succeed".equals(testCase)) {
      tester.fullDuplexCallShouldSucceed();
    } else if ("half_duplex_call_should_succeed".equals(testCase)) {
      tester.halfDuplexCallShouldSucceed();
    } else if ("server_streaming_should_be_flow_controlled".equals(testCase)) {
      tester.serverStreamingShouldBeFlowControlled();
    } else if ("very_large_request".equals(testCase)) {
      try {
        tester.veryLargeRequest();
      } catch (AssumptionViolatedException e) {
        // This test case requires more memory than most Android devices have available
        Log.w(LOG_TAG, "Skipping " + testCase + " due to assumption violation", e);
      }
    } else if ("very_large_response".equals(testCase)) {
      try {
        tester.veryLargeResponse();
      } catch (AssumptionViolatedException e) {
        // This test case requires more memory than most Android devices have available
        Log.w(LOG_TAG, "Skipping " + testCase + " due to assumption violation", e);
      }
    } else if ("deadline_not_exceeded".equals(testCase)) {
      tester.deadlineNotExceeded();
    } else if ("deadline_exceeded".equals(testCase)) {
      tester.deadlineExceeded();
    } else if ("deadline_exceeded_server_streaming".equals(testCase)) {
      tester.deadlineExceededServerStreaming();
    } else if ("unimplemented_method".equals(testCase)) {
      tester.unimplementedMethod();
    } else if ("timeout_on_sleeping_server".equals(testCase)) {
      tester.timeoutOnSleepingServer();
    } else if ("graceful_shutdown".equals(testCase)) {
      tester.gracefulShutdown();
    } else {
      throw new IllegalArgumentException("Unimplemented/Unknown test case: " + testCase);
    }
  }

  private static class Tester extends AbstractInteropTest {

    private Tester(ManagedChannel channel) {
      this.channel = channel;
    }

    @Override
    protected ManagedChannel createChannel() {
      return channel;
    }

    @Override
    protected ManagedChannelBuilder<?> createChannelBuilder() {
      throw new UnsupportedOperationException();
    }

    @Override
    protected boolean metricsExpected() {
      return false;
    }
  }
}
