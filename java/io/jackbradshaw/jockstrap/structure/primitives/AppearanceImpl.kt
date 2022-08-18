package io.jackbradshaw.jockstrap.structure.primitives

import com.jme3.scene.Node
import io.jackbradshaw.jockstrap.elements.ComponentId
import io.jackbradshaw.jockstrap.physics.Placement
import io.jackbradshaw.jockstrap.physics.toJMonkeyTransform
import io.jackbradshaw.klu.flow.NiceFlower
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class AppearanceImpl(
        override val id: ComponentId,
        override val source: io.jackbradshaw.jockstrap.structure.controllers.Item,
) : io.jackbradshaw.jockstrap.structure.bases.BasePrimitive<Appearance>(), io.jackbradshaw.jockstrap.structure.primitives.Appearance {

  private val guard = Mutex()

  override val appearance = NiceFlower<Appearance>(Node("default_id_for_component_id_$id")) {
    guard.withLock {
      it.placeAt(placement.get())
    }
  }

  override fun intrinsic() = appearance.asFlow()

  override suspend fun placeIntrinsic(placement: Placement) {
    guard.withLock {
      appearance.get().placeAt(placement)
    }
  }

  private suspend fun Appearance.placeAt(placement: Placement) {
    setLocalTransform(placement.toJMonkeyTransform())
  }
}