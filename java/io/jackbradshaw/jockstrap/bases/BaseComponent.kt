package io.jackbradshaw.jockstrap.bases

import com.google.protobuf.MessageLite
import io.jackbradshaw.jockstrap.physics.Placement
import com.google.protobuf.ByteString
import io.jackbradshaw.jockstrap.elements.Component
import io.jackbradshaw.jockstrap.elements.ComponentId
import io.jackbradshaw.jockstrap.elements.ComponentSnapshot
import io.jackbradshaw.jockstrap.elements.Entity
import io.jackbradshaw.jockstrap.physics.placeZero
import kotlinx.coroutines.flow.MutableStateFlow
import io.jackbradshaw.klu.flow.NiceFlower

abstract class BaseComponent<I> : Component<I> {

  override val placement = NiceFlower<Placement>(placeZero) {
    placeIntrinsic(it)
  }

  final override suspend fun restore(snapshot: ComponentSnapshot) {
    preRestore(snapshot)
    placement.set(snapshot.placement)
    contributeToRestoration(snapshot)
    postRestore(snapshot)
  }

  final override suspend fun snapshot(): ComponentSnapshot {
    preSnapshot()
    return ComponentSnapshot.newBuilder()
      .apply {
        setComponentId(id)
        setPlacement(this@BaseComponent.placement.get())
        contributeToSnapshot()?.let {
          setExtras(ByteString.copyFrom(it.toByteArray()))
        }
      }
      .build()
      .also { postSnapshot(it) }
  }

  protected open suspend fun placeIntrinsic(placement: Placement) = Unit

  protected open suspend fun preRestore(snapshot: ComponentSnapshot) = Unit

  protected open suspend fun contributeToRestoration(snapshot: ComponentSnapshot) = Unit

  protected open suspend fun postRestore(snapshot: ComponentSnapshot) = Unit

  protected open suspend fun preSnapshot() = Unit

  protected open suspend fun contributeToSnapshot(): MessageLite? = null

  protected open suspend fun postSnapshot(result: ComponentSnapshot) = Unit
}