package com.jackbradshaw.sealant.manifold

import com.jackbradshaw.sealant.hub.HubTest

abstract class ManifoldTest<T, M : Manifold<T>> : HubTest<T, M>() {
    // Shared testing logic for Manifolds will go here.
}
