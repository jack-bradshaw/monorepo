package io.jackbradshaw.otter.scene.content

import io.jackbradshaw.klu.flow.NiceFlower
import kotlinx.coroutines.Job

class HostImpl : Host {

  override suspend fun setRootItem(item: Item<*>) {
    rootItem.set(item)
  }

  override suspend fun clearRootItem() {
    rootItem.set(null)
  }

  override suspend fun getRootItem() = rootItem.get()

  override suspend fun findItemById(id: String): Item<*> {
    TODO()
  }

  override suspend fun findItemByPrimitive(primitive: Any): Item<*> {
    TODO()
  }
}