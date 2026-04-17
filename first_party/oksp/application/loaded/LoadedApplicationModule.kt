package com.jackbradshaw.oksp.application.loaded

import com.jackbradshaw.oksp.application.Application
import com.jackbradshaw.oksp.application.loaded.apploader.ApplicationLoader
import dagger.Module
import dagger.Provides

@Module
object LoadedApplicationModule {
  @Provides fun provideApplication(loader: ApplicationLoader): Application = loader.load()
}
