package io.matthewbradshaw.jockstrap.sensation

import com.jme3.math.ColorRGBA

/**
 * Creates a new JMonkey Engine 3 Color which is equivalent to this.
 */
fun Color.toJMonkeyColor() = ColorRGBA(red, green, blue, alpha)

/**
 * Creates a new Jockstrap [Color] which is equivalent to this.
 */
fun ColorRGBA.toJockstrapColor() = color(red = r, green = g, blue = b, alpha = a)