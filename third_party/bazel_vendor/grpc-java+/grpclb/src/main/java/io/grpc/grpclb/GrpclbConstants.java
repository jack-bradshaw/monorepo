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

package io.grpc.grpclb;

import io.grpc.Attributes;
import io.grpc.EquivalentAddressGroup;
import io.grpc.ExperimentalApi;
import io.grpc.Metadata;
import java.util.List;

/**
 * Constants for the GRPCLB load-balancer.
 */
@ExperimentalApi("https://github.com/grpc/grpc-java/issues/1782")
public final class GrpclbConstants {

  /**
   * The opaque token given by the remote balancer for each returned server address.  The client
   * will send this token with any requests sent to the associated server.
   */
  public static final Metadata.Key<String> TOKEN_METADATA_KEY =
      Metadata.Key.of("lb-token", Metadata.ASCII_STRING_MARSHALLER);

  /**
   * For passing LB tokens via the EAG attributes.
   */
  @EquivalentAddressGroup.Attr
  static final Attributes.Key<String> TOKEN_ATTRIBUTE_KEY =
      Attributes.Key.create("lb-token");

  /**
   * Attribute key for gRPC LB server addresses.
   */
  public static final Attributes.Key<List<EquivalentAddressGroup>> ATTR_LB_ADDRS =
      Attributes.Key.create("io.grpc.grpclb.GrpclbConstants.ATTR_LB_ADDRS");

  /**
   * The naming authority of a gRPC LB server address.  It is an address-group-level attribute,
   * present when the address group is a LoadBalancer.
   */
  @EquivalentAddressGroup.Attr
  public static final Attributes.Key<String> ATTR_LB_ADDR_AUTHORITY =
      Attributes.Key.create("io.grpc.grpclb.GrpclbConstants.ATTR_LB_ADDR_AUTHORITY");

  /**
   * Whether this EquivalentAddressGroup was provided by a GRPCLB server. It would be rare for this
   * value to be {@code false}; generally it would be better to not have the key present at all.
   */
  @EquivalentAddressGroup.Attr
  public static final Attributes.Key<Boolean> ATTR_LB_PROVIDED_BACKEND =
      Attributes.Key.create("io.grpc.grpclb.GrpclbConstants.ATTR_LB_PROVIDED_BACKEND");

  private GrpclbConstants() { }
}
