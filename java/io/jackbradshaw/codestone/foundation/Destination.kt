package io.jackbradshaw.codestone.foundation

import io.jackbradshaw.codestone.sustainment.primitives.Sustainable
import io.jackbradshaw.codestone.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.codestone.ui.primitives.Usable
import io.jackbradshaw.codestone.ui.primitives.Usable.Ui

/** A location in the application that can be given focus. */
interface Destination<U : Ui, O: Operation<*>> : Usable<U>, Sustainable<O>
