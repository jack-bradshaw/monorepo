package com.jackbradshaw.concurrency.quinn.testing.hub

import com.jackbradshaw.chronosphere.idleable.Idleable
import com.jackbradshaw.concurrency.quinn.Quinn

/**
 * A [Quinn.Factory] which tracks all provisioned instances.
 *
 * Reports [Idleable.isIdle] iff all provisioned instances are idle
 */
interface IdleableQuinnHub : Quinn.Factory, Idleable
