package java.io.matthewbradshaw.merovingian.core

import com.google.protobuf.MessageLite

interface Restorable<S: MessageLite> {
  val stator: Stator<S>
}