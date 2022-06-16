package io.matthewbradshaw.octavius

import io.matthewbradshaw.octavius.core.Paradigm

fun otto(paradigm: Paradigm): Octavius = DaggerOctavius.builder().paradigm(paradigm).build()