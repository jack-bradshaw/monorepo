package com.jackbradshaw.oksp.application

import com.jackbradshaw.oksp.application.loader.ApplicationLoader
import dagger.Module
import dagger.Provides

@Module
object ApplicationModule {
  @Provides fun provideApplication(loader: ApplicationLoader): Application = loader.load()
}
