package io.matthewbradshaw.merovingian.host

import io.matthewbradshaw.merovingian.engine.Engine
import io.matthewbradshaw.merovingian.model.WorldItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import com.jme3.bullet.collision.PhysicsCollisionObject
import javax.inject.Inject
import com.jme3.scene.Spatial
import io.matthewbradshaw.merovingian.coroutines.physicsPreTickDispatcher
import io.matthewbradshaw.merovingian.coroutines.renderingDispatcher

import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers

class HostImpl @Inject internal constructor(
  private val engine: Engine,
) : Host {

  private val mutex = Mutex()
  private var activeRepresentation: Spatial? = null
  private var activePhysics: PhysicsCollisionObject? = null
  private var activeLogic: Job? = null

  override suspend fun run(item: WorldItem) {
    mutex.withLock {
      engine.extractCoroutineScope().launch {
        withContext(Dispatchers.Default) {
          activeLogic?.let { it.cancel() }
          activePhysics?.let { engine.extractPhysics().getPhysicsSpace().remove(it) }
        }
        withContext(engine.renderingDispatcher()) {
          activeRepresentation?.let { engine.extractFrameworkNode().detachChild(it) }
          activeRepresentation = item.representation().also { engine.extractApplicationNode().attachChild(it) }
        }
        withContext(Dispatchers.Default) {
          activePhysics = item.physics().also { engine.extractPhysics().getPhysicsSpace().add(it) }
          activeLogic = launch { item.logic() }
        }
      }
    }
  }
}