/*
 * Copyright 2020 The gRPC Authors
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

package io.grpc.rls;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.base.Ticker;
import io.grpc.internal.FakeClock;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class AdaptiveThrottlerTest {
  private static final float TOLERANCE = 0.0001f;

  private final FakeClock fakeClock = new FakeClock();
  private final Ticker fakeTicker = fakeClock.getTicker();
  private final AdaptiveThrottler throttler =
      new AdaptiveThrottler.Builder()
          .setHistorySeconds(1)
          .setRatioForAccepts(1.0f)
          .setRequestsPadding(1)
          .setTicker(fakeTicker)
          .build();

  @Test
  public void shouldThrottle() {
    long startTime = fakeClock.currentTimeMillis();

    // initial states
    assertThat(throttler.requestStat.get(fakeTicker.read())).isEqualTo(0L);
    assertThat(throttler.throttledStat.get(fakeTicker.read())).isEqualTo(0L);
    assertThat(throttler.getThrottleProbability(fakeTicker.read()))
        .isWithin(TOLERANCE).of(0.0f);

    // Request 1, allowed by all.
    assertThat(throttler.shouldThrottle(0.4f)).isFalse();
    fakeClock.forwardTime(1L, TimeUnit.MILLISECONDS);
    throttler.registerBackendResponse(false);

    assertThat(throttler.requestStat.get(fakeTicker.read()))
        .isEqualTo(1L);
    assertThat(throttler.throttledStat.get(fakeTicker.read())).isEqualTo(0L);
    assertThat(throttler.getThrottleProbability(fakeTicker.read()))
        .isWithin(TOLERANCE).of(0.0f);

    // Request 2, throttled by backend
    assertThat(throttler.shouldThrottle(0.4f)).isFalse();
    fakeClock.forwardTime(1L, TimeUnit.MILLISECONDS);
    throttler.registerBackendResponse(true);

    assertThat(throttler.requestStat.get(fakeTicker.read()))
        .isEqualTo(2L);
    assertThat(throttler.throttledStat.get(fakeTicker.read()))
        .isEqualTo(1L);
    assertThat(throttler.getThrottleProbability(fakeTicker.read()))
        .isWithin(TOLERANCE)
        .of(1.0f / 3.0f);

    // Skip to half second mark from the beginning (half the duration).
    fakeClock.forwardTime(500 - (fakeClock.currentTimeMillis() - startTime),
        TimeUnit.MILLISECONDS);

    // Request 3, throttled by backend
    assertThat(throttler.shouldThrottle(0.4f)).isFalse();
    fakeClock.forwardTime(1L, TimeUnit.MILLISECONDS);
    throttler.registerBackendResponse(true);

    assertThat(throttler.requestStat.get(fakeTicker.read())).isEqualTo(3L);
    assertThat(throttler.throttledStat.get(fakeTicker.read())).isEqualTo(2L);
    assertThat(throttler.getThrottleProbability(fakeTicker.read()))
        .isWithin(TOLERANCE)
        .of(2.0f / 4.0f);

    // Request 4, throttled by client.
    assertThat(throttler.shouldThrottle(0.4f)).isTrue();
    fakeClock.forwardTime(1L, TimeUnit.MILLISECONDS);

    assertThat(throttler.requestStat.get(fakeTicker.read())).isEqualTo(4L);
    assertThat(throttler.throttledStat.get(fakeTicker.read())).isEqualTo(3L);
    assertThat(throttler.getThrottleProbability(fakeTicker.read()))
        .isWithin(TOLERANCE)
        .of(3.0f / 5.0f);

    // Skip to the point where only requests 3 and 4 are visible.
    fakeClock.forwardTime(
        1250 - (fakeClock.currentTimeMillis() - startTime), TimeUnit.MILLISECONDS);

    assertThat(throttler.requestStat.get(fakeTicker.read())).isEqualTo(2L);
    assertThat(throttler.throttledStat.get(fakeTicker.read())).isEqualTo(2L);
    assertThat(throttler.getThrottleProbability(fakeTicker.read()))
        .isWithin(TOLERANCE)
        .of(2.0f / 3.0f);
  }

  /**
   * Check that when the ticker returns a negative value for now that the slot detection logic
   * is correctly handled and then when the value transitions from negative to positive that things
   * continue to work correctly.
   */
  @Test
  public void negativeTickerValues() {
    long rewindAmount = TimeUnit.MILLISECONDS.toNanos(300) + fakeClock.getTicker().read();
    fakeClock.forwardTime(-1 * rewindAmount, TimeUnit.NANOSECONDS);
    assertThat(fakeClock.getTicker().read()).isEqualTo(TimeUnit.MILLISECONDS.toNanos(-300));
    shouldThrottle();
  }
}
