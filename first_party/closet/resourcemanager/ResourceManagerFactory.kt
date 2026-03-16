package com.jackbradshaw.closet.resourcemanager

import com.jackbradshaw.closet.observable.ObservableClosable

interface ResourceManagerFactory {
  fun <K, V : ObservableClosable> createResourceManager(): ResourceManager<K, V>
}
