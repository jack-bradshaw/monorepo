package io.matthewbradshaw.merovingian.testing

import io.matthewbradshaw.merovingian.model.GameItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.math.acos
import kotlin.math.cos
import io.matthewbradshaw.merovingian.model.Game
import com.jme3.scene.Node
import com.jme3.scene.Spatial
import kotlin.math.sin
import kotlin.random.Random
import com.jme3.material.Material
import com.jme3.math.ColorRGBA
import com.jme3.scene.Geometry
import com.jme3.scene.shape.Box
import com.jme3.math.Vector3f
import com.jme3.renderer.Camera
import com.google.auto.factory.Provided
import com.google.auto.factory.AutoFactory

@TestingScope
@AutoFactory
class CubeGame(
  @Provided private val materials: Materials,
  @Provided private val camera: Camera,
  @Provided private val cubeSwarmFactory: CubeSwarmFactory
) : Game {

  private lateinit var swarm: CubeSwarm

  override suspend fun prepare() {
    if (this::swarm.isInitialized) return
    swarm = cubeSwarmFactory.create()
  }

  override fun representation() = flowOf(swarm)

  override suspend fun logic() {
    camera.setLocation(Vector3f(0f, 0f, 0f))
  }
}