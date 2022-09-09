package io.jackbradshaw.otter.structure.bases

import io.jackbradshaw.otter.structure.controllers.Component
import io.jackbradshaw.otter.structure.controllers.ComponentId

/**
 * A convenience implementation of [Component] that does all the heavy lifting. This class implements many of the
 * interface functions which reduces the work on the end-engineer but takes away some control. Engineers who need access
 * to these functions can instead override the pre* and post* functions to receive callbacks when the functions enter
 * and exit.
 */
abstract class ComponentBase : Component {

}