package com.jackbradshaw.backstab.core.host

import com.jackbradshaw.backstab.core.repository.Repository

/**
 * An abstraction for the environment housing the Backstab processor. It provides core dependencies
 * that the Backstab core processing requires, most notably the [Repository].
 */
interface HostComponent {
  fun repository(): Repository
}
