package com.jackbradshaw.closet.resourcemanager

import com.jackbradshaw.closet.observable.ObservableClosable
import com.jackbradshaw.coroutines.io.Io
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class ResourceManagerFactoryImpl @Inject internal constructor(
  @Io private val coroutineScope: CoroutineScope
) : ResourceManager.Factory {
  override fun <K, V : ObservableClosable> createResourceManager(): ResourceManager<K, V> = ResourceManagerImpl(coroutineScope)
}
