package java.io.matthewbradshaw.merovingian.core

import com.google.protobuf.MessageLite

interface Stator<S : MessageLite> {
  fun restoreFrom(state: S)
  fun takeSnapshot(): S
}