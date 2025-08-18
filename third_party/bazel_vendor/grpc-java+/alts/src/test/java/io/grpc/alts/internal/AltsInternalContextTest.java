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

package io.grpc.alts.internal;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** Unit tests for {@link AltsInternalContext}. */
@RunWith(JUnit4.class)
public final class AltsInternalContextTest {
  private static final int TEST_MAX_RPC_VERSION_MAJOR = 3;
  private static final int TEST_MAX_RPC_VERSION_MINOR = 5;
  private static final int TEST_MIN_RPC_VERSION_MAJOR = 2;
  private static final int TEST_MIN_RPC_VERSION_MINOR = 1;
  private static final SecurityLevel TEST_SECURITY_LEVEL = SecurityLevel.INTEGRITY_AND_PRIVACY;
  private static final String TEST_APPLICATION_PROTOCOL = "grpc";
  private static final String TEST_LOCAL_SERVICE_ACCOUNT = "local@gserviceaccount.com";
  private static final String TEST_PEER_SERVICE_ACCOUNT = "peer@gserviceaccount.com";
  private static final String TEST_RECORD_PROTOCOL = "ALTSRP_GCM_AES128";
  private static final String TEST_PEER_ATTRIBUTES_KEY = "peer";
  private static final String TEST_PEER_ATTRIBUTES_VALUE = "attributes";

  private Map<String, String> testPeerAttributes;
  private HandshakerResult handshakerResult;
  private RpcProtocolVersions rpcVersions;

  @Before
  public void setUp() {
    testPeerAttributes = new HashMap<String, String>();
    testPeerAttributes.put(TEST_PEER_ATTRIBUTES_KEY, TEST_PEER_ATTRIBUTES_VALUE);
    rpcVersions =
        RpcProtocolVersions.newBuilder()
            .setMaxRpcVersion(
                RpcProtocolVersions.Version.newBuilder()
                    .setMajor(TEST_MAX_RPC_VERSION_MAJOR)
                    .setMinor(TEST_MAX_RPC_VERSION_MINOR)
                    .build())
            .setMinRpcVersion(
                RpcProtocolVersions.Version.newBuilder()
                    .setMajor(TEST_MIN_RPC_VERSION_MAJOR)
                    .setMinor(TEST_MIN_RPC_VERSION_MINOR)
                    .build())
            .build();
    Identity.Builder peerIdentity = Identity.newBuilder()
        .setServiceAccount(TEST_PEER_SERVICE_ACCOUNT);
    peerIdentity.putAllAttributes(testPeerAttributes);
    handshakerResult =
        HandshakerResult.newBuilder()
            .setApplicationProtocol(TEST_APPLICATION_PROTOCOL)
            .setRecordProtocol(TEST_RECORD_PROTOCOL)
            .setPeerIdentity(peerIdentity)
            .setLocalIdentity(Identity.newBuilder().setServiceAccount(TEST_LOCAL_SERVICE_ACCOUNT))
            .setPeerRpcVersions(rpcVersions)
            .build();
  }

  @Test
  public void testAltsInternalContext() {
    AltsInternalContext context = new AltsInternalContext(handshakerResult);
    assertEquals(TEST_APPLICATION_PROTOCOL, context.getApplicationProtocol());
    assertEquals(TEST_RECORD_PROTOCOL, context.getRecordProtocol());
    assertEquals(TEST_SECURITY_LEVEL, context.getSecurityLevel());
    assertEquals(TEST_PEER_SERVICE_ACCOUNT, context.getPeerServiceAccount());
    assertEquals(TEST_LOCAL_SERVICE_ACCOUNT, context.getLocalServiceAccount());
    assertEquals(rpcVersions, context.getPeerRpcVersions());
    assertEquals(testPeerAttributes, context.getPeerAttributes());
    assertEquals(TEST_PEER_ATTRIBUTES_VALUE, context.getPeerAttributes()
        .get(TEST_PEER_ATTRIBUTES_KEY));
  }
}
