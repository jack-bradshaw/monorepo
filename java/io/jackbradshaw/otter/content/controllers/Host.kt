package io.jackbradshaw.otter.scene.content

interface Host {
  suspend fun items(): Set<Item>

  suspend fun addItem(item: Item)
  suspend fun removeItem(item: Item)
  suspend fun removeAllItems()

  suspend fun itemRemoved(): Flow<Item>
  suspend fun itemAdded(): Flow<Item>

  suspend fun findItemWithPrimitive(primitive: Primitive): Item
}