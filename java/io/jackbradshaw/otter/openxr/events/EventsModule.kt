package io.jackbradshaw.otter.openxr.events

import dagger.Binds
import dagger.Module

@Module
interface EventsModule {
  @Binds fun bindEvents(impl: EventsImpl): Events
}
