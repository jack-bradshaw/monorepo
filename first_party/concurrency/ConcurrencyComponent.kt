package com.jackbradshaw.concurrency

import com.jackbradshaw.concurrency.pulsar.Pulsar

interface ConcurrencyComponent {
  fun pulsar(): Pulsar
}
