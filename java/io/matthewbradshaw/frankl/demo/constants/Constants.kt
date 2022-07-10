package io.matthewbradshaw.frankl.demo.config

object Constants {
  /**
   * Items are grouped into channels so material effects can be applied across them by modifying only a small number
   * of material objects. This value defines the number of independent channels.
   */
  const val ITEM_CHANNELS = 10

  /**
   * The number of items in the swarm.
   */
  const val SWARM_SIZE = 500
}