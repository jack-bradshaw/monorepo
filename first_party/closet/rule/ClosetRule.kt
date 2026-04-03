package com.jackbradshaw.closet.rule

import org.junit.rules.TestRule

interface ClosetRule<out T : AutoCloseable> : TestRule {
  fun get(): T
}
