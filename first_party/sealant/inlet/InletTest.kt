package com.jackbradshaw.sealant.inlet

import com.jackbradshaw.sealant.hub.HubTest

abstract class InletTest<T, I : Inlet<T>> : HubTest<T, I>() {
    // Shared testing logic for Inlets will go here.
}
