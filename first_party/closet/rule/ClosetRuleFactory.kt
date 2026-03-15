package com.jackbradshaw.closet.rule

object ClosetRuleFactory {
  fun <T: AutoCloseable> create(resource: T): ClosetRule<T> = object : ClosetRuleTemplate<T>() {
    override fun initialiseResource() = resource
  }
}
