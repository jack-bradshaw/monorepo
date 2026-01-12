package com.jackbradshaw.backstab.tests

import org.junit.Test
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import dagger.Module

class BackstabIntegrationTest {

    @Test
    fun `generated module exists and is usable`() {
        // Direct reference proves it exists on classpath
        val module = SimpleComponentAutoModule
        assertNotNull(module)
        
        // precise class check
        assertTrue("Should be an object", SimpleComponentAutoModule::class.objectInstance != null)
        assertTrue("Should be annotated with @Module", SimpleComponentAutoModule::class.annotations.any { it is Module })
        
        // Check provider method via reflection (method name is stable)
        val method = SimpleComponentAutoModule::class.java.getDeclaredMethod("provideSimpleComponent")
        assertNotNull(method)
    }
}
