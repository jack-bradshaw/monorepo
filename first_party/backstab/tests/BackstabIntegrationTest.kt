package com.jackbradshaw.backstab.tests

import org.junit.Test
import org.junit.Assert.assertNotNull
import kotlin.reflect.full.memberFunctions

class BackstabIntegrationTest {

    @Test
    fun `generated module exists`() {
        // Check if SimpleComponentAutoModule class exists
        val moduleClass = Class.forName("com.jackbradshaw.backstab.tests.SimpleComponentAutoModule")
        assertNotNull(moduleClass)
        
        // Check if provider method exists
        val providerMethod = moduleClass.declaredMethods.find { it.name == "provideSimpleComponent" }
        assertNotNull("Provider method should exist", providerMethod)
    }
}
