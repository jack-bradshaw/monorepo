package io.jackbradshaw.otter.graphics

fun color(red: Float = 0f, green: Float = 0f, blue: Float = 0f, alpha: Float = 1f) = Color.newBuilder()
  .setRed(red).setBlue(blue).setGreen(green).setAlpha(alpha).build()

fun Color.takeRed() = color(red, green = 0f, blue = 0f, alpha = alpha)
fun Color.takeGreen() = color(red = 0f, green, blue = 0f, alpha = alpha)
fun Color.takeBlue() = color(red = 0f, green = 0f, blue, alpha = alpha)
fun Color.takeAlpha() = color(red = 0f, green = 0f, blue = 0f, alpha = alpha)

fun Color.withRed(red: Float = 1f) = color(red, green, blue, alpha)
fun Color.withGreen(green: Float = 1f) = color(red, green, blue, alpha)
fun Color.withBlue(blue: Float = 1f) = color(red, green, blue, alpha)
fun Color.withAlpha(alpha: Float = 1f) = color(red, green, blue, alpha)

fun interpolate(color1: Color, color2: Color, proportion: Float) : Color {
  val inverseProportion = 1 - proportion
  return color(
    red = color1.red * inverseProportion + color2.red * proportion,
    green = color1.green * inverseProportion + color2.green * proportion,
    blue = color1.blue * inverseProportion + color2.blue * proportion,
    alpha = color1.alpha * inverseProportion + color2.alpha * proportion
  )
}

val transparent = color(red = 0f, green = 0f,blue =  0f, alpha = 0f)
val red = color(red = 1f, alpha = 1f)
val green = color(green = 1f, alpha = 1f)
val blue = color(blue = 1f, alpha = 1f)
val cyan = color(red = 0f, green = 1f, blue = 1f, alpha = 1f)
val magenta = color(red = 1f, green = 0f, blue = 1f, alpha = 1f)
val yellow = color(red = 1f, green = 1f, blue = 0f, alpha = 1f)
val white = color(red = 1f, green = 1f, blue = 1f, alpha = 1f)
val black = color(red = 0f, green = 0f, blue = 0f, alpha = 1f)

fun grey(proportion: Float) = interpolate(white, black, proportion)