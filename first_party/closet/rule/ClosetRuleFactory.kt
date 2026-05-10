package com.jackbradshaw.closet.rule

import com.jackbradshaw.closet.observable.SuspendableClosable

/** A factory for producing [ClosetRule] instances. */
object ClosetRuleFactory {
  /**
   * Creates a new [ClosetRule] that manages the provided [resource]. A new instance is returned on
   * each call.
   */
  fun <T : SuspendableClosable> create(resource: T): ClosetRule<T> =
      object : ClosetRuleTemplate<T>() {
        override fun initialiseResource() = resource
      }
}
