package io.matthewbradshaw.gmonkey.ui

import io.matthewbradshaw.gmonkey.lifecycle.Pausable
import io.matthewbradshaw.gmonkey.lifecycle.Restorable
import com.google.protobuf.MessageLite
import io.matthewbradshaw.gmonkey.lifecycle.Preparable
import com.jme3.scene.Spatial

interface Item<S : MessageLite> : Restorable<S>, Preparable, Pausable {
  fun ui(): Spatial
}