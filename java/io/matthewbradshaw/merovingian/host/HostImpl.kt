package io.matthewbradshaw.merovingian.host

import com.jme3.bullet.collision.PhysicsCollisionObject
import com.jme3.scene.Spatial
import io.matthewbradshaw.merovingian.coroutines.physicsDispatcher
import io.matthewbradshaw.merovingian.coroutines.renderingDispatcher
import io.matthewbradshaw.merovingian.engine.Engine
import io.matthewbradshaw.merovingian.model.WorldItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HostImpl @Inject internal constructor(
  private val engine: Engine,
) : Host {

  private val mutex = Mutex()
  private var activeVisuals: Spatial? = null
  private var activePhysics: PhysicsCollisionObject? = null
  private var activeLogic: Job? = null

  override suspend fun run(item: WorldItem) {
    mutex.withLock {
      engine.extractCoroutineScope().launch {
        withContext(Dispatchers.Default) {
          activeLogic?.let { it.cancel() }
        }
        withContext(engine.physicsDispatcher()) {
          activePhysics?.let { engine.extractPhysics().getPhysicsSpace().remove(it) }
        }
        withContext(engine.renderingDispatcher()) {
          activeVisuals?.let { engine.extractFrameworkNode().detachChild(it) }
          activeVisuals = item.visual().also { engine.extractApplicationNode().attachChild(it) }
        }
        withContext(engine.physicsDispatcher()) {
          activePhysics = item.physical()?.also { engine.extractPhysics().getPhysicsSpace().add(it) }
        }
        withContext(Dispatchers.Default) {
          activeLogic = launch { item.logical() }
        }
      }
    }
  }
}