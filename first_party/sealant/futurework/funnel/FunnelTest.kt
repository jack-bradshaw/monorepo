package com.jackbradshaw.sealant.funnel

import com.jackbradshaw.sealant.hub.HubTest

abstract class FunnelTest<T, F : Funnel<T>> : HubTest<T, F>() {
    // Shared testing logic for Funnels will go here.
}
