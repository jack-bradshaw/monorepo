package io.matthewbradshaw.gmonkey.core.ignition

import io.matthewbradshaw.gmonkey.core.CoreScope
import javax.inject.Inject
import com.jme3.app.SimpleApplication

@CoreScope
class IgnitionImpl @Inject internal constructor(private val app: SimpleApplication) : Ignition {
  override suspend fun go() {
    app.start()
  }
}