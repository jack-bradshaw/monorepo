package com.jackbradshaw.closet.resourcemanager

import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import com.jackbradshaw.coroutines.io.Io
import com.jackbradshaw.closet.observable.ObservableClosable

class ResourceManagerFactoryImpl @Inject internal constructor(
  @Io private val coroutineScope: CoroutineScope
) : ResourceManagerFactory {
  override fun <K, V : ObservableClosable> createResourceManager(): ResourceManager<K, V> = ResourceManagerImpl(coroutineScope)
}
