/*
 * Copyright 2015 The gRPC Authors
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

import com.google.common.base.Stopwatch;
import com.google.common.base.Supplier;
import com.google.common.base.Ticker;
import com.google.common.util.concurrent.AbstractFuture;
import io.grpc.Deadline;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Delayed;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * A manipulated clock that exports a {@link Ticker} and a {@link ScheduledExecutorService}.
 *
 * <p>To simulate the locking scenario of using real executors, it never runs tasks within {@code
 * schedule()} or {@code execute()}. Instead, you should call {@link #runDueTasks} in your test
 * method to run all due tasks. {@link #forwardTime} and {@link #forwardNanos} call {@link
 * #runDueTasks} automatically.
 */
public final class FakeClock {

  private static final TaskFilter ACCEPT_ALL_FILTER = new TaskFilter() {
      @Override
      public boolean shouldAccept(Runnable command) {
        return true;
      }
    };

  private final ScheduledExecutorService scheduledExecutorService = new ScheduledExecutorImpl();

  private final PriorityBlockingQueue<ScheduledTask> scheduledTasks = new PriorityBlockingQueue<>();
  private final LinkedBlockingQueue<ScheduledTask> dueTasks = new LinkedBlockingQueue<>();

  private final Ticker ticker =
      new Ticker() {
        @Override public long read() {
          return currentTimeNanos;
        }
      };

  private final Deadline.Ticker deadlineTicker =
      new Deadline.Ticker() {
        @Override public long nanoTime() {
          return currentTimeNanos;
        }
      };

  private final Supplier<Stopwatch> stopwatchSupplier =
      new Supplier<Stopwatch>() {
        @Override public Stopwatch get() {
          return Stopwatch.createUnstarted(ticker);
        }
      };

  private final TimeProvider timeProvider =
      new TimeProvider() {
        @Override
        public long currentTimeNanos() {
          return currentTimeNanos;
        }
      };

  private long currentTimeNanos;

  public class ScheduledTask extends AbstractFuture<Void> implements ScheduledFuture<Void> {
    public final Runnable command;
    public long dueTimeNanos;

    ScheduledTask(Runnable command) {
      this.command = command;
    }

    void run() {
      command.run();
      set(null);
    }

    void setDueTimeNanos(long dueTimeNanos) {
      this.dueTimeNanos = dueTimeNanos;
    }

    @Override public boolean cancel(boolean mayInterruptIfRunning) {
      scheduledTasks.remove(this);
      dueTasks.remove(this);
      return super.cancel(mayInterruptIfRunning);
    }

    @Override public long getDelay(TimeUnit unit) {
      return unit.convert(dueTimeNanos - currentTimeNanos, TimeUnit.NANOSECONDS);
    }

    @Override public int compareTo(Delayed other) {
      ScheduledTask otherTask = (ScheduledTask) other;
      if (dueTimeNanos > otherTask.dueTimeNanos) {
        return 1;
      } else if (dueTimeNanos < otherTask.dueTimeNanos) {
        return -1;
      } else {
        return 0;
      }
    }

    @Override
    public String toString() {
      return "[due=" + dueTimeNanos + ", task=" + command + "]";
    }
  }

  private class ScheduledExecutorImpl implements ScheduledExecutorService {
    @Override public <V> ScheduledFuture<V> schedule(
        Callable<V> callable, long delay, TimeUnit unit) {
      throw new UnsupportedOperationException();
    }

    private void schedule(ScheduledTask task, long delay, TimeUnit unit) {
      task.setDueTimeNanos(currentTimeNanos + unit.toNanos(delay));
      if (delay > 0) {
        scheduledTasks.add(task);
      } else {
        dueTasks.add(task);
      }
    }

    @Override public ScheduledFuture<?> schedule(Runnable cmd, long delay, TimeUnit unit) {
      ScheduledTask task = new ScheduledTask(cmd);
      schedule(task, delay, unit);
      return task;
    }

    @Override public ScheduledFuture<?> scheduleAtFixedRate(
        Runnable cmd, long initialDelay, long period, TimeUnit unit) {
      ScheduledTask task = new ScheduleAtFixedRateTask(cmd, period, unit);
      schedule(task, initialDelay, unit);
      return task;
    }

    @Override public ScheduledFuture<?> scheduleWithFixedDelay(
        Runnable cmd, long initialDelay, long delay, TimeUnit unit) {
      ScheduledTask task = new ScheduleWithFixedDelayTask(cmd, delay, unit);
      schedule(task, initialDelay, unit);
      return task;
    }

    @Override public boolean awaitTermination(long timeout, TimeUnit unit) {
      throw new UnsupportedOperationException();
    }

    @Override public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) {
      throw new UnsupportedOperationException();
    }

    @Override public <T> List<Future<T>> invokeAll(
        Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) {
      throw new UnsupportedOperationException();
    }

    @Override public <T> T invokeAny(Collection<? extends Callable<T>> tasks) {
      throw new UnsupportedOperationException();
    }

    @Override public <T> T invokeAny(
        Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) {
      throw new UnsupportedOperationException();
    }

    @Override public boolean isShutdown() {
      // If shutdown is not implemented, then it is never shutdown.
      return false;
    }

    @Override public boolean isTerminated() {
      throw new UnsupportedOperationException();
    }

    @Override public void shutdown() {
      throw new UnsupportedOperationException();
    }

    @Override public List<Runnable> shutdownNow() {
      throw new UnsupportedOperationException();
    }

    @Override public <T> Future<T> submit(Callable<T> task) {
      throw new UnsupportedOperationException();
    }

    @Override public Future<?> submit(Runnable task) {
      throw new UnsupportedOperationException();
    }

    @Override public <T> Future<T> submit(Runnable task, T result) {
      throw new UnsupportedOperationException();
    }

    @Override public void execute(Runnable command) {
      // Since it is being enqueued immediately, no point in tracing the future for cancellation.
      Future<?> unused = schedule(command, 0, TimeUnit.NANOSECONDS);
    }

    class ScheduleAtFixedRateTask extends ScheduledTask {
      final long periodNanos;

      public ScheduleAtFixedRateTask(Runnable command, long period, TimeUnit unit) {
        super(command);
        this.periodNanos = unit.toNanos(period);
      }

      @Override void run() {
        long startTimeNanos = currentTimeNanos;
        command.run();
        if (!isCancelled()) {
          schedule(this, startTimeNanos + periodNanos - currentTimeNanos, TimeUnit.NANOSECONDS);
        }
      }
    }

    class ScheduleWithFixedDelayTask extends ScheduledTask {

      final long delayNanos;

      ScheduleWithFixedDelayTask(Runnable command, long delay, TimeUnit unit) {
        super(command);
        this.delayNanos = unit.toNanos(delay);
      }

      @Override
      void run() {
        command.run();
        if (!isCancelled()) {
          schedule(this, delayNanos, TimeUnit.NANOSECONDS);
        }
      }
    }
  }

  /**
   * Provides a partially implemented instance of {@link ScheduledExecutorService} that uses the
   * fake clock ticker for testing.
   */
  public ScheduledExecutorService getScheduledExecutorService() {
    return scheduledExecutorService;
  }

  /**
   * Provides a {@link TimeProvider} that is backed by this FakeClock.
   */
  public TimeProvider getTimeProvider() {
    return timeProvider;
  }

  /**
   * Provides a stopwatch instance that uses the fake clock ticker.
   */
  public Supplier<Stopwatch> getStopwatchSupplier() {
    return stopwatchSupplier;
  }

  /**
   * Ticker of the FakeClock.
   */
  public Ticker getTicker() {
    return ticker;
  }

  /**
   * Deadline ticker of the FakeClock.
   */
  public Deadline.Ticker getDeadlineTicker() {
    return deadlineTicker;
  }

  /**
   * Run all due tasks. Immediately due tasks that are queued during the process also get executed.
   *
   * @return the number of tasks run by this call
   */
  public int runDueTasks() {
    int count = 0;
    while (true) {
      checkDueTasks();
      if (dueTasks.isEmpty()) {
        break;
      }
      ScheduledTask task;
      while ((task = dueTasks.poll()) != null) {
        task.run();
        count++;
      }
    }
    return count;
  }

  private void checkDueTasks() {
    while (true) {
      ScheduledTask task = scheduledTasks.peek();
      if (task == null || task.dueTimeNanos > currentTimeNanos) {
        break;
      }
      if (scheduledTasks.remove(task)) {
        dueTasks.add(task);
      }
    }
  }

  /**
   * Return all due tasks.
   */
  public Collection<ScheduledTask> getDueTasks() {
    checkDueTasks();
    return new ArrayList<>(dueTasks);
  }

  /**
   * Return all unrun tasks.
   */
  public Collection<ScheduledTask> getPendingTasks() {
    return getPendingTasks(ACCEPT_ALL_FILTER);
  }

  /**
   * Return all unrun tasks accepted by the given filter.
   */
  public Collection<ScheduledTask> getPendingTasks(TaskFilter filter) {
    ArrayList<ScheduledTask> result = new ArrayList<>();
    for (ScheduledTask task : dueTasks) {
      if (filter.shouldAccept(task.command)) {
        result.add(task);
      }
    }
    for (ScheduledTask task : scheduledTasks) {
      if (filter.shouldAccept(task.command)) {
        result.add(task);
      }
    }
    return result;
  }

  /**
   * Forward the time by the given duration and run all due tasks.
   *
   * @return the number of tasks run by this call
   */
  public int forwardTime(long value, TimeUnit unit) {
    currentTimeNanos += unit.toNanos(value);
    return runDueTasks();
  }

  /**
   * Forward the time by the given nanoseconds and run all due tasks.
   *
   * @return the number of tasks run by this call
   */
  public int forwardNanos(long nanos) {
    return forwardTime(nanos, TimeUnit.NANOSECONDS);
  }

  /**
   * Return the number of queued tasks.
   */
  public int numPendingTasks() {
    return dueTasks.size() + scheduledTasks.size();
  }

  /**
   * Return the number of queued tasks accepted by the given filter.
   */
  public int numPendingTasks(TaskFilter filter) {
    int count = 0;
    for (ScheduledTask task : dueTasks) {
      if (filter.shouldAccept(task.command)) {
        count++;
      }
    }
    for (ScheduledTask task : scheduledTasks) {
      if (filter.shouldAccept(task.command)) {
        count++;
      }
    }
    return count;
  }

  public long currentTimeMillis() {
    // Normally millis and nanos are of different epochs. Add an offset to simulate that.
    return TimeUnit.NANOSECONDS.toMillis(currentTimeNanos + 1234567890123456789L);
  }

  /**
   * A filter that allows us to have fine grained control over which tasks are accepted for certain
   * operation.
   */
  public interface TaskFilter {
    /**
     * Inspect the Runnable and returns true if it should be accepted.
     */
    boolean shouldAccept(Runnable runnable);
  }
}
