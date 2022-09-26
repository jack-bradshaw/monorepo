package io.jackbradshaw.clearxr.events

import dagger.Module
import dagger.Binds

@Module
interface EventsModule {
  @Binds
  fun bindEvents(impl: EventsImpl): Events
}