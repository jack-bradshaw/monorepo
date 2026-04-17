package com.jackbradshaw.oksp.application.loaded

import com.jackbradshaw.oksp.application.Application
import com.jackbradshaw.oksp.application.ApplicationComponent
import com.jackbradshaw.oksp.application.loaded.classloader.ClassLoaderModule
import com.jackbradshaw.oksp.application.loaded.apploader.ApplicationLoaderImplModule
import dagger.Component

@Component(
    modules =
        [LoadedApplicationModule::class, ApplicationLoaderImplModule::class, ClassLoaderModule::class])
interface LoadedApplicationComponent : ApplicationComponent

fun loadedApplicationComponent(): ApplicationComponent = DaggerLoadedApplicationComponent.create()
