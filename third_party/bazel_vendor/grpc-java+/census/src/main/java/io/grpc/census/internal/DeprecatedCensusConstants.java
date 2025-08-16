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

package io.grpc.census.internal;

import com.google.common.annotations.VisibleForTesting;
import io.opencensus.contrib.grpc.metrics.RpcMeasureConstants;
import io.opencensus.stats.Measure.MeasureDouble;
import io.opencensus.stats.Measure.MeasureLong;

/** Holder class for the deprecated OpenCensus constants. */
@SuppressWarnings("deprecation")
@VisibleForTesting
public final class DeprecatedCensusConstants {
  public static final MeasureLong RPC_CLIENT_ERROR_COUNT =
      RpcMeasureConstants.RPC_CLIENT_ERROR_COUNT;
  public static final MeasureDouble RPC_CLIENT_UNCOMPRESSED_REQUEST_BYTES =
      RpcMeasureConstants.RPC_CLIENT_UNCOMPRESSED_REQUEST_BYTES;
  public static final MeasureDouble RPC_CLIENT_UNCOMPRESSED_RESPONSE_BYTES =
      RpcMeasureConstants.RPC_CLIENT_UNCOMPRESSED_RESPONSE_BYTES;
  public static final MeasureLong RPC_CLIENT_FINISHED_COUNT =
      RpcMeasureConstants.RPC_CLIENT_FINISHED_COUNT;

  public static final MeasureLong RPC_SERVER_ERROR_COUNT =
      RpcMeasureConstants.RPC_SERVER_ERROR_COUNT;
  public static final MeasureDouble RPC_SERVER_SERVER_ELAPSED_TIME =
      RpcMeasureConstants.RPC_SERVER_SERVER_ELAPSED_TIME;
  public static final MeasureDouble RPC_SERVER_UNCOMPRESSED_REQUEST_BYTES =
      RpcMeasureConstants.RPC_SERVER_UNCOMPRESSED_REQUEST_BYTES;
  public static final MeasureDouble RPC_SERVER_UNCOMPRESSED_RESPONSE_BYTES =
      RpcMeasureConstants.RPC_SERVER_UNCOMPRESSED_RESPONSE_BYTES;
  public static final MeasureLong RPC_SERVER_FINISHED_COUNT =
      RpcMeasureConstants.RPC_SERVER_FINISHED_COUNT;

  private DeprecatedCensusConstants() {}
}
