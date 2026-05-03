package com.jackbradshaw.obelisk.core.adapters

interface InflowAdapter<I, A> {
  fun ingest(abstractSyntaxTreeRootElements: Set<I>): Ingestion<I, A>
}

data class Ingestion<I, A>(
  val translated: Map<I, Set<A>> = emptyMap(),
  val unused: Set<I> = emptySet()
)
