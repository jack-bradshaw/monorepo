package io.jackbradshaw.jockstrap.structure.bases

import io.jackbradshaw.jockstrap.structure.controllers.Integration
import io.jackbradshaw.jockstrap.structure.controllers.IntegrationId

/**
 * A convenience implementation of [Integration] that does all the heavy lifting. This class implements many of the
 * interface functions which reduces the work on the end-engineer but takes away some control. Engineers who need access
 * to these functions can instead override the pre* and post* functions to receive callbacks when the functions enter
 * and exit.
 */
abstract class IntegrationBase : Integration {

}