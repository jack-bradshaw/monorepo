package io.jackbradshaw.queen.foundation

import io.jackbradshaw.queen.sustainment.primitives.Sustainable
import io.jackbradshaw.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.queen.ui.primitives.Usable
import io.jackbradshaw.queen.ui.primitives.Usable.Ui

/** A location in the application that can be given focus. */
interface Destination<U : Ui, O: Operation<*>> : Usable<U>, Sustainable<O>
