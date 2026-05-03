package com.jackbradshaw.oksp.host

import com.google.devtools.ksp.processing.SymbolProcessorProvider

interface Host : SymbolProcessorProvider

/*
so... what does host actually do?

its hard to test becaues it has no interface functions.

it has only emergent properties.



one shot things it dos

when run launches application runs oncreate does nothing more until tear down triggered by serivce
*/
