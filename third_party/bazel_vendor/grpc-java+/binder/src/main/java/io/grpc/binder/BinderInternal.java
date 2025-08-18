/*
 * Copyright 2022 The gRPC Authors
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

package io.grpc.binder;

import android.os.IBinder;
import io.grpc.Internal;
import io.grpc.binder.internal.BinderTransportSecurity;

/** Helper class to expose IBinderReceiver methods for legacy internal builders. */
@Internal
public class BinderInternal {

  /** Sets the receiver's {@link IBinder} using {@link IBinderReceiver#set(IBinder)}. */
  public static void setIBinder(IBinderReceiver receiver, IBinder binder) {
    receiver.set(binder);
  }

  /**
   * Creates a {@link BinderTransportSecurity.ServerPolicyChecker} from a {@link
   * ServerSecurityPolicy}. This exposes to callers an interface to check security policies without
   * causing hard dependencies on a specific class.
   */
  public static BinderTransportSecurity.ServerPolicyChecker createPolicyChecker(
      ServerSecurityPolicy securityPolicy) {
    return securityPolicy::checkAuthorizationForServiceAsync;
  }
}
