package io.jackbradshaw.otter.openxr.events

import dagger.Binds

@Module
interface EventsModule {
  @Binds fun bindEvents(impl: EventsImpl): Events
}
