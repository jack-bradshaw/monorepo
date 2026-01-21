package com.jackbradshaw.codestone.interaction.usable

/** Contains a user interface. */
interface Usable<out U : Usable.Ui> {

  /** The user interface. */
  val ui: U

  /** A user interface element */
  interface Ui
}
