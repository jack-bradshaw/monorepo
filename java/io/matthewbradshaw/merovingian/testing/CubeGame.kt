package io.matthewbradshaw.merovingian.testing

import io.matthewbradshaw.merovingian.model.GameItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.flow.flowOf
import com.jme3.math.Vector3f
import com.jme3.renderer.Camera
import com.google.auto.factory.Provided
import com.google.auto.factory.AutoFactory
import com.jme3.scene.Spatial

@TestingScope
@AutoFactory
class CubeGame(
  @Provided private val camera: Camera,
  @Provided private val cubeSwarmFactory: CubeSwarmFactory
) : GameItem {

  private val prepared = Mutex()
  private lateinit var swarm: CubeSwarm

  override suspend fun prepare() {
    if (!this::swarm.isInitialized) {
      swarm = cubeSwarmFactory.create()
    }
  }

  override suspend fun representation() : Spatial {
    return swarm.representation()
  }

  override suspend fun logic() {
    camera.setLocation(Vector3f(0f, 0f, 0f))
  }
}