package com.jackbradshaw.concurrency.quinn.testing.idleable

import com.jackbradshaw.chronosphere.idleable.Idleable
import com.jackbradshaw.concurrency.quinn.Quinn

interface IdleableQuinn<T> : Quinn<T>, Idleable {
  interface Hub : Quinn.Factory, Idleable
}