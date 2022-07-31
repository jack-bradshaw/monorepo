package io.matthewbradshaw.jockstrap.model.components

import com.jme3.light.AmbientLight
import com.jme3.light.DirectionalLight
import com.jme3.light.Light
import com.jme3.light.PointLight
import com.jme3.light.SpotLight
import io.matthewbradshaw.jockstrap.math.toJMonkeyVector
import io.matthewbradshaw.jockstrap.model.bases.BaseLightingComponent.Behavior.Point
import io.matthewbradshaw.jockstrap.model.bases.BaseLightingComponent.Behavior.Spot
import io.matthewbradshaw.jockstrap.model.bases.AmbientLightConfig
import io.matthewbradshaw.jockstrap.model.bases.SpotLightConfig
import io.matthewbradshaw.jockstrap.model.bases.PointLightConfig
import io.matthewbradshaw.jockstrap.model.bases.BaseLightingComponent.Behavior.Ambient
import io.matthewbradshaw.jockstrap.model.bases.BaseLightingComponent.Behavior.Directional
import io.matthewbradshaw.jockstrap.model.elements.ComponentId
import io.matthewbradshaw.jockstrap.model.elements.Entity
import io.matthewbradshaw.jockstrap.physics.Placement
import io.matthewbradshaw.jockstrap.sensation.Color
import io.matthewbradshaw.jockstrap.sensation.toJMonkeyColor
import io.matthewbradshaw.jockstrap.sensation.white
import io.matthewbradshaw.klu.flow.NiceFlower

class JMonkeyLightingComponent(
  override val id: ComponentId,
  override val source: Entity,
) : BaseComponent<Light>, LightingComponent {

  private val light = NiceFlower<Light>(AmbientLight())

  val color = NiceFlower(white) {
    light.get().setColor(it.toJMonkeyColor())
  }

  val behavior = NiceFlower<BaseLightingComponent.Behavior<*>>(
    Behavior.Ambient(AmbientLightConfig.newBuilder().build())
  ) {
    val newLight = when (it) {
      is Spot -> SpotLight().apply { configureTo(it.config) }
      is Point -> PointLight().apply { configureTo(it.config) }
      is Ambient -> AmbientLight()
      is Directional -> DirectionalLight()
    }
    newLight.placeAt(placement.get())
    newLight.tint(color.get())
    light.set(newLight)
  }

  override fun intrinsic() = light.asFlow()

  protected final override suspend fun placeIntrinsic(placement: Placement) {
    light.get().placeAt(placement)
  }

  protected final override suspend fun contributeToSnapshot(): LightingIntrinsicSnapshot {
    return LightingIntrinsicSnapshot.newBuilder()
      .setColor(color.get())
      .setBehavior(behavior.get())
      .build()
  }

  protected final override suspend fun contributeToRestoration(snapshot: ComponentSnapshot) {
    val contribution = LightingIntrinsicSnapshot.parseFrom(snapshot.extras)
    color.set(contribution.color)
    behavior.set(contribution.behavior)
  }

  private suspend fun Light.placeAt(placement: Placement) {
    when (light) {
      is SpotLight -> {
        light.setPosition(placement.position.toJMonkeyVector())
        TODO() // apply rotation as direction
      }
      is PointLight -> light.setPosition(placement.position.toJMonkeyVector())
      is AmbientLight -> {
        // Ambient light exists equally everywhere, no need to place anything.
      }
      is DirectionalLight -> {
        TODO() // apply rotation as direction
      }
    }
  }

  private suspend fun Light.tint(color: Color) {
    light.get().setColor(color.toJMonkeyColor())
  }

  private suspend fun SpotLight.configureTo(config: SpotLightConfig) {
    setSpotRange(config.rangeRadius)
    setSpotInnerAngle(config.innerAngleRadians)
    setSpotOuterAngle(config.outerAngleRadians)
  }

  private suspend fun PointLight.configureTo(config: PointLightConfig) {
    setRadius(config.rangeRadius)
  }
}

