package com.jackbradshaw.concurrency.quinn.testing.prod

import com.jackbradshaw.concurrency.quinn.Quinn

/** Provides a production instance of [Quinn.Factory] with the `@Prod` qualifier applied. */
interface ProdPassThroughComponent {
  @Prod fun prodQuinnFactory(): Quinn.Factory
}
