package com.jackbradshaw.coroutines.testing.artificial.dispatcher

import com.jackbradshaw.coroutines.CoroutinesDaggerScope
import dagger.Component
import javax.inject.Inject
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AdvancableDispatcherImplTest : AdvancableDispatcherTest() {

  @Inject internal lateinit var dispatcher: AdvancableDispatcherImpl

  @Before
  fun setUp() {
    DaggerAdvancableDispatcherTestComponent.create().inject(this)
  }

  override fun subject() = dispatcher
}

@CoroutinesDaggerScope
@Component(modules = [AdvancableDispatcherModule::class])
interface AdvancableDispatcherTestComponent {
  fun inject(test: AdvancableDispatcherImplTest)
}
