package com.jackbradshaw.closet.resourcemanager

import com.jackbradshaw.closet.observable.ObservableClosable

/** Creates instances of  */
interface ResourceManagerFactory {
  fun <K, V : ObservableClosable> createResourceManager(): ResourceManager<K, V>
}
