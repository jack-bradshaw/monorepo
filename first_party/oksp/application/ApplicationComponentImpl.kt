package com.jackbradshaw.oksp.application

import com.jackbradshaw.oksp.application.classloader.ClassLoaderModule
import com.jackbradshaw.oksp.application.loader.ApplicationLoaderImplModule
import dagger.Component

@Component(
    modules =
        [ApplicationModule::class, ApplicationLoaderImplModule::class, ClassLoaderModule::class])
interface ApplicationComponentImpl : ApplicationComponent

fun applicationComponent(): ApplicationComponent = DaggerApplicationComponentImpl.create()
