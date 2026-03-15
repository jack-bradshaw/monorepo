package com.jackbradshaw.closet.resourcemanager

import com.jackbradshaw.closet.resourcemanager.ResourceManager.ManagedResource

interface ResourceManagerFactory {
  fun <K, V : ManagedResource> createResourceManager(): ResourceManager<K, V>
}
