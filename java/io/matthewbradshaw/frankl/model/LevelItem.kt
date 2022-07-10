package io.matthewbradshaw.frankl.model

import com.jme3.bullet.collision.PhysicsCollisionObject
import com.jme3.light.Light
import com.jme3.scene.Node
import io.matthewbradshaw.klu.flow.BinaryDeltaFlow
import io.matthewbradshaw.frankl.model.LevelItemSnapshot
import kotlinx.coroutines.flow.flowOf

interface LevelItem {
  fun coordinatorNode(): Node
  fun exportedLighting(): BinaryDeltaFlow<Light> = flowOf()
  fun exportedColliders(): BinaryDeltaFlow<PhysicsCollisionObject> = flowOf()

  val id: LevelItemId

  suspend fun getDescendant(id: LevelItemId): LevelItem

  suspend fun onAttachToHost(snapshot: LevelItemSnapshot?) = Unit
  suspend fun onStatic() = Unit
  suspend fun onDynamic() = Unit
  suspend fun onDetachFromHost()
  suspend fun takeSnapshot(): LevelItemSnapshot = LevelItemSnapshot.newBuilder().build()
}