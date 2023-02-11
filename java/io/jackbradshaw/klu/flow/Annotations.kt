package io.jackbradshaw.klu.flow

/**
 * Denotes a [Flow] that begins emitting values when created. This is contrasted with a [Cold] flow which does not emit
 * values until collection occurs.
 */
annotation class HotFlow

/**
 * Denotes a [Flow] that begins emitting items when collected. This is contrasted with a [Hot] flow which emits values
 * when created.
 */
annotation class ColdFlow

/**
 * Denotes a [Flow] that remains open indefinitely. The flow may still close itself, but the collector should not depend
 * on this occurring.
 */
annotation class IndefiniteFlow