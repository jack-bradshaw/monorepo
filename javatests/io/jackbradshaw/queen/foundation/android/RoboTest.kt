package io.jackbradshaw.queen.foundation.android

import org.junit.Test;
import org.junit.Rule
import org.junit.Before;
import io.jackbradshaw.queen.ui.platforms.android.AndroidComposeUi
import io.jackbradshaw.queen.ui.platforms.android.AndroidViewUi
import org.junit.runner.RunWith;
import androidx.compose.foundation.layout.Box
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxSize
import org.hamcrest.CoreMatchers.not
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.ui.graphics.Color as ComposeColor
import android.graphics.Color as ViewColor
import androidx.compose.material.Text 
import org.robolectric.Robolectric
import io.jackbradshaw.queen.sustainment.operations.ktCoroutineOperation
import org.robolectric.RobolectricTestRunner;
import io.jackbradshaw.sustainment.primitives.Sustainable.Operation
import kotlinx.coroutines.launch
import kotlinx.coroutines.GlobalScope
import org.robolectric.annotation.Config;
import org.robolectric.RuntimeEnvironment
import android.content.Context

import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle.State
import android.util.Log
import kotlinx.coroutines.runBlocking
import androidx.test.core.app.ActivityScenario
import org.robolectric.shadows.ShadowContextWrapper
import android.content.ComponentName
import kotlinx.coroutines.delay
import android.content.Intent
import android.app.Activity
import android.app.Application
import org.robolectric.shadow.api.Shadow;
import org.robolectric.Shadows.shadowOf
import com.google.common.truth.Truth.assertThat
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.test.core.app.ApplicationProvider

@RunWith(RobolectricTestRunner::class)
@Config(application = AndroidRoot::class, instrumentedPackages = ["androidx.loader.content"])
class RoboTest {

  @get:Rule val composeRule = createEmptyComposeRule()
  
  private lateinit var root: AndroidRoot<String>
  private val receiver = MonoReceiver()

  @Before
  fun setup() {
    root = ApplicationProvider.getApplicationContext() as AndroidRoot<String>
    root.onCreate()
    root.coordinator.navigator.value = TestNavigator()
  }

  @Test fun activityNotLaunched_signalFromEnvironment_toReceiver_activityIntendedByRoot() {
    deliverSignalFromEnvironmentToReceiver()
    delayForAsync()

    assertMonoActivityIntendedByRoot()
  }

  @Test fun activityNotLaunched_signalFromEnvironment_toReceiver_resolvesToComposable_displaysComposable() {
    deliverSignalFromEnvironmentToReceiver(Destination.COMPOSE)
    delayForAsync()
    processNextIntent()
    delayForAsync()

    assertComposeDisplayed()
  }

  @Test fun activityNotLaunched_signalFromEnvironment_toReceiver_resolvesToView_displaysView() {
    deliverSignalFromEnvironmentToReceiver(Destination.VIEW)
    delayForAsync()
    processNextIntent()
    delayForAsync()

    assertViewDisplayed()
  }

  @Test fun activityNotLaunched_signalFromEnvironment_toReceiver_resolvesToNoDestination_activityNotIntended() {
    deliverSignalFromEnvironmentToReceiver(destination = null)
    delayForAsync()

    assertMonoActivityNotIntended()
  }

  @Test fun activityNotLaunched_signalFromEnvironment_toActivity_activityIntendedBySystem() {
    deliverSignalFromEnvironmentToActivity()
    delayForAsync()

    assertMonoActivityIntendedBySystem()
  }

  @Test fun activityNotLaunched_signalFromEnvironment_toActivity_resolvesToComposable_displaysComposable() {
    deliverSignalFromEnvironmentToActivity(Destination.COMPOSE)
    delayForAsync()
    processNextIntent()
    delayForAsync()

    assertComposeDisplayed()
  }

  @Test fun activityNotLaunched_signalFromEnvironment_toActivity_resolvesToView_displaysView() {
    deliverSignalFromEnvironmentToActivity(Destination.VIEW)
    delayForAsync()
    processNextIntent()
    delayForAsync()

    assertViewDisplayed()
  }

  @Test fun activityNotLaunched_signalFromEnvironment_toActivity_resolvesToNoDestination_nothingDisplayed() {
    deliverSignalFromEnvironmentToActivity(destination = null)
    delayForAsync()
    processNextIntent()
    delayForAsync()

    assertNothingDisplayed()
  }

  @Test fun activityAlreadyLaunched_signalFromEnvironment_toReceiver_activityNotIntended() {
    setupActivity()

    deliverSignalFromEnvironmentToReceiver()
    delayForAsync()

    assertMonoActivityNotIntended()
  }

  @Test fun activityAlreadyLaunched_signalFromEnvironment_toReciever_resolvesToComposable_displaysComposable() {
    setupActivity(initialDestination = Destination.VIEW)

    deliverSignalFromEnvironmentToReceiver(Destination.COMPOSE)
    delayForAsync()

    assertComposeDisplayed()
  }

  @Test fun activityAlreadyLaunched_signalFromEnvironment_toReciever_resolvesToView_displaysView() {
    setupActivity(initialDestination = Destination.COMPOSE)

    deliverSignalFromEnvironmentToReceiver(Destination.VIEW)
    delayForAsync()

    assertViewDisplayed()
  }

  @Test fun activityAlreadyLaunched_signalFromEnvironment_toReciever_resolvesToNoDestination_displaysNothing() {
    setupActivity()

    deliverSignalFromEnvironmentToReceiver(destination = null)
    delayForAsync()

    assertNothingDisplayed()
  }

  @Test fun activityAlreadyLaunched_signalFromApplication_rootDoesNotLaunchMonoActivity() {
    setupActivity()

    deliverSignalFromApplication()
    delayForAsync()

    assertMonoActivityNotIntended()
  }

  @Test fun activityAlreadyLaunched_signalFromApplication_resolvesToComposable_displaysComposable() {
    setupActivity(initialDestination = Destination.VIEW)

    deliverSignalFromApplication(Destination.COMPOSE)
    delayForAsync()

    assertComposeDisplayed()
  }

  @Test fun activityAlreadyLaunched_signalFromApplication_resolvesToView_displaysView() {
    setupActivity(initialDestination = Destination.COMPOSE)

    deliverSignalFromApplication(Destination.VIEW)
    delayForAsync()

    assertViewDisplayed()
  }

  private fun deliverSignalFromEnvironmentToReceiver(destination: Destination? = Destination.COMPOSE) {
    val intent = Intent().apply {
      if (destination != null) putExtra(TestNavigator.INTENT_KEY, destination.name)
    }
    receiver.onReceive(root, intent)
  }

  private fun deliverSignalFromEnvironmentToActivity(destination: Destination? = Destination.COMPOSE) {
    val intent = Intent(root, MonoActivity::class.java).apply {
      if (destination != null) putExtra(TestNavigator.INTENT_KEY, destination.name)
      addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    root.startActivity(intent)
  }

  private fun deliverSignalFromApplication(destination: Destination = Destination.COMPOSE) {
    root.coordinator.onSignalFromApplication(destination.name)
  }

  private fun processNextIntent() {
    val shadowContext = Shadow.extract<ShadowContextWrapper>(root)
    val intent = shadowContext.getNextStartedActivity()
    val scenario = ActivityScenario.launch<MonoActivity>(intent)
    scenario.moveToState(State.RESUMED)
  }

  /** Verifies the MonoActivity was launched by the root. */
  private fun assertMonoActivityIntendedByRoot() {
    val shadowContext = Shadow.extract<ShadowContextWrapper>(root)
    val intent = shadowContext.getNextStartedActivity()

    assertThat(intent.component).isEqualTo(ComponentName(root, MonoActivity::class.java))
    assertThat(intent.hasExtra(MonoActivity.LAUNCHED_BY_ROOT)).isTrue()
  }

  /** Verifies the MonoActivity was launched by the system (i.e. the launcher). */
  private fun assertMonoActivityIntendedBySystem() {
    val shadowContext = Shadow.extract<ShadowContextWrapper>(root)
    val intent = shadowContext.getNextStartedActivity()

    assertThat(intent.component).isEqualTo(ComponentName(root, MonoActivity::class.java))
    assertThat(intent.hasExtra(MonoActivity.LAUNCHED_BY_ROOT)).isFalse()
  }

  /** Verifies the MonoActivity was not launched. */
  private fun assertMonoActivityNotIntended() {
    val shadowContext = Shadow.extract<ShadowContextWrapper>(root)
    val intent = shadowContext.getNextStartedActivity()
  
    assertThat(intent).isNull()
  }
  
  private fun assertComposeDisplayed() {
    composeRule.onNodeWithText(ComposeDestination.TEXT).assertIsDisplayed()
  }
  
  
  private fun assertViewDisplayed() {
    onView(withText(ViewDestination.TEXT)).check(matches(isDisplayed()))
  }

  private fun assertNothingDisplayed() {
    onView(withText(ViewDestination.TEXT)).check(doesNotExist())
    composeRule.onNodeWithText(ComposeDestination.TEXT).assertIsNotDisplayed()
  }

  private fun setupActivity(initialDestination: Destination = Destination.VIEW) {
    deliverSignalFromEnvironmentToActivity(initialDestination)
    delayForAsync()
    processNextIntent()
    delayForAsync()
  }

  private fun delayForAsync() {
    runBlocking {
      delay(500L)
    }
  }
}

/** Supported UI destinations. */
private enum class Destination {
  COMPOSE,
  VIEW;

  companion object {
    fun from(name: String?): Destination? {
      return if (name == null) { 
        null
      } else {
        try { valueOf(name) } catch (e: IllegalArgumentException) { null }
      }
    }
  }
}

private class TestNavigator : AndroidNavigator<String, Operation<*>> {
  override fun translateSignalFromApplication(signal: String): AndroidDestination<Operation<*>>? {
    return when (Destination.from(name = signal)) {
      Destination.COMPOSE -> ComposeDestination()
      Destination.VIEW -> ViewDestination()
      else -> null
    }
  }

  override fun translateSignalFromEnvironment(signal: Intent): AndroidDestination<Operation<*>>?  {
    return translateSignalFromApplication(
      signal.getStringExtra(TestNavigator.INTENT_KEY) ?: ""
    )
  }

  override val operation = ktCoroutineOperation { GlobalScope.launch {} }

  companion object {
    /** When intents are received from the environment the navigator loads config data from the
     * string extra associated with this key. The specific literal is not important, but
     * extras need love like everything does. */
    const val INTENT_KEY = "love"
  }
}

class ComposeDestination : AndroidDestination<Operation<*>> {
  override val ui = object : AndroidComposeUi {
    @Composable
    override fun composition() {
      Box(
        modifier = Modifier.fillMaxSize().background(ComposeColor.Red),
        contentAlignment = Alignment.Center
      ) {
        Text(text = TEXT)
      }
    }
  }

  override val operation = ktCoroutineOperation(GlobalScope) {}

  companion object {
    /** The text displayed in the compose-based UI. */
    const val TEXT = "this is compose UI"
  }
}

class ViewDestination : AndroidDestination<Operation<*>> {
  override val ui = object : AndroidViewUi {
    override fun view(context: Context) = object : FrameLayout(context) {
      init {
        layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)
        setBackgroundColor(ViewColor.BLUE)

        val text = TextView(context).apply {
          layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT, Gravity.CENTER)
          text = TEXT
        }
        addView(text)
      }
    }
  }

  override val operation = ktCoroutineOperation(GlobalScope) {}

  companion object {
    /** The text displayed in the view-based UI. */
    const val TEXT = "this is view UI"
  }
}
