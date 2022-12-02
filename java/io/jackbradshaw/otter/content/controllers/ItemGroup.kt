package java.io.jackbradshaw.otter.content.controllers

interface ItemGroup {
  fun items(): Set<Item>
  fun itemAdded(): Flow<Item>
  fun itemRemoved(): Flow<Item>
}