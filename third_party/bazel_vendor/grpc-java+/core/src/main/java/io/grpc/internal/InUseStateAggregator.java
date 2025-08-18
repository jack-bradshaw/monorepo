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

package io.grpc.internal;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;
import javax.annotation.concurrent.NotThreadSafe;

/**
 * Aggregates the in-use state of a set of objects.
 */
@NotThreadSafe
public abstract class InUseStateAggregator<T> {

  private final Set<T> inUseObjects = Collections.newSetFromMap(new IdentityHashMap<T,Boolean>());

  /**
   * Update the in-use state of an object. Initially no object is in use.
   *
   * <p>This may call into {@link #handleInUse} or {@link #handleNotInUse} when appropriate.
   */
  public final void updateObjectInUse(T object, boolean inUse) {
    int origSize = inUseObjects.size();
    if (inUse) {
      inUseObjects.add(object);
      if (origSize == 0) {
        handleInUse();
      }
    } else {
      boolean removed = inUseObjects.remove(object);
      if (removed && origSize == 1) {
        handleNotInUse();
      }
    }
  }

  public final boolean isInUse() {
    return !inUseObjects.isEmpty();
  }

  /**
   * Returns {@code true} if any of the given objects are in use.
   *
   * @param objects The objects to consider.
   * @return {@code true} if any of the given objects are in use.
   */
  public final boolean anyObjectInUse(Object... objects) {
    for (Object object : objects) {
      if (inUseObjects.contains(object)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Called when the aggregated in-use state has changed to true, which means at least one object is
   * in use.
   */
  protected abstract void handleInUse();

  /**
   * Called when the aggregated in-use state has changed to false, which means no object is in use.
   */
  protected abstract void handleNotInUse();
}
