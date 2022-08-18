package io.jackbradshaw.jockstrap.structure.primitives

import com.jme3.light.DirectionalLight
import com.jme3.light.PointLight
import com.jme3.light.SpotLight
import io.jackbradshaw.jockstrap.math.toJMonkeyVector
import io.jackbradshaw.jockstrap.components.AmbientConfig
import io.jackbradshaw.jockstrap.components.PointConfig
import io.jackbradshaw.jockstrap.components.SpotConfig
import io.jackbradshaw.jockstrap.elements.ComponentId
import io.jackbradshaw.jockstrap.elements.ComponentSnapshot
import io.jackbradshaw.jockstrap.physics.Placement
import io.jackbradshaw.jockstrap.graphics.Color
import io.jackbradshaw.jockstrap.graphics.toJMonkeyColor
import io.jackbradshaw.jockstrap.graphics.white
import io.jackbradshaw.klu.flow.NiceFlower
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class LightImpl(
        override val id: ComponentId,
        override val source: io.jackbradshaw.jockstrap.structure.controllers.Item,
) : io.jackbradshaw.jockstrap.structure.bases.BasePrimitive<Light>(), io.jackbradshaw.jockstrap.structure.primitives.Light {

  private val guard = Mutex()

  private val light = NiceFlower<Light>(AmbientLight())

  override val color = NiceFlower(white) {
    guard.withLock {
      light.get().setColor(it.toJMonkeyColor())
    }
  }

  override val behavior = NiceFlower<LightingComponentBehavior>(
    LightingComponentBehavior.newBuilder().setAmbientConfig(AmbientConfig.newBuilder().build()).build()
  ) {
    guard.withLock {
      val newLight = when {
        it.hasSpotConfig() -> SpotLight().apply { configureTo(it.spotConfig) }
        it.hasPointConfig() -> PointLight().apply { configureTo(it.pointConfig) }
        it.hasAmbientConfig() -> AmbientLight()
        it.hasDirectionalConfig() -> DirectionalLight()
        else -> throw IllegalStateException("Missing or unsupported lighting behavior in $it.")
      }
      newLight.placeAt(placement.get())
      newLight.tint(color.get())
      light.set(newLight)
    }
  }

  override fun intrinsic() = light.asFlow()

  protected override suspend fun placeIntrinsic(placement: Placement) {
    guard.withLock {
      light.get().placeAt(placement)
    }
  }

  protected override suspend fun contributeToSnapshot(): LightingComponentSnapshot {
    guard.withLock {
      return LightingComponentSnapshot.newBuilder()
        .setColor(color.get())
        .setBehavior(behavior.get())
        .build()
    }
  }

  protected override suspend fun contributeToRestoration(snapshot: ComponentSnapshot) {
    guard.withLock {
      val contribution = LightingComponentSnapshot.parseFrom(snapshot.extras)
      color.set(contribution.color)
      behavior.set(contribution.behavior)
    }
  }

  private suspend fun Light.placeAt(placement: Placement) {
    when (this) {
      is SpotLight -> {
        setPosition(placement.position.toJMonkeyVector())
        TODO() // apply rotation as direction
      }
      is PointLight -> setPosition(placement.position.toJMonkeyVector())
      is AmbientLight -> {
        // Ambient light exists equally everywhere, no need to place anything.
      }
      is DirectionalLight -> {
        TODO() // apply rotation as direction
      }
    }
  }

  private suspend fun Light.tint(color: Color) {
    setColor(color.toJMonkeyColor())
  }

  private suspend fun SpotLight.configureTo(config: SpotConfig) {
    setSpotRange(config.rangeRadius)
    setSpotInnerAngle(config.innerAngleRadians)
    setSpotOuterAngle(config.outerAngleRadians)
  }

  private suspend fun PointLight.configureTo(config: PointConfig) {
    setRadius(config.rangeRadius)
  }
}

