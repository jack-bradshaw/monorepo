package io.jackbradshaw.queen.foundation.android


import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import android.view.ViewGroup.LayoutParams
import androidx.compose.runtime.mutableStateOf
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.util.Log
import androidx.compose.ui.viewinterop.AndroidView
import  androidx.compose.ui.platform.ComposeView
import  androidx.compose.ui.platform.ViewCompositionStrategy
import io.jackbradshaw.queen.ui.primitives.Usable.Ui
import io.jackbradshaw.queen.ui.platforms.android.AndroidComposeUi
import io.jackbradshaw.queen.ui.platforms.android.AndroidUi
import android.os.Handler
import androidx.compose.material.Text
import io.jackbradshaw.queen.ui.platforms.android.AndroidViewUi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


/**
* Observes the destination hosted by the [Coordinator] in [AndroidRoot] and displays it.
*/
open class MonoActivity : AppCompatActivity() {


 private var updateUiJob: Job? = null


 private val ui = mutableStateOf<AndroidUi?>(null)
 private val thing = mutableStateOf<Int>(0)


 override fun onCreate(savedInstanceState: Bundle?) {
   super.onCreate(savedInstanceState)
   setTheme(R.style.MonoActivityTheme)


   // Some intents bypass the mono receiver. Manually re-route them for normal handling.
   getIntent().let {
     // If an intend does not have the TAG, it did not come from the root
     if (it.getExtras()?.getBoolean(LAUNCHED_BY_ROOT) == false) {
       // TODO see if this can be an replaced with a real broadcast to the system horizon
       MonoReceiver().onReceive(context = this, it)
     }
   }


  
 }

 override public fun onNewIntent(intent: Intent) {
  // Some intents bypass the mono receiver. Manually re-route them for normal handling.
    // If an intend does not have the TAG, it did not come from the root
    if (intent.getExtras()?.getBoolean(LAUNCHED_BY_ROOT) == false) {
      // TODO see if this can be an replaced with a real broadcast to the system horizon
      MonoReceiver().onReceive(context = this, intent)
    }
 }


 override fun onResume() {
   super.onResume()

   

   updateUiJob =
       GlobalScope.launch {
         val root = getApplicationContext()
         check(root is AndroidRoot<*>) {
           "Application must be an AndroidRoot."
         }
         root.coordinator.destination.map { it?.ui }.onEach { ui ->
           Handler(getMainLooper()).post {
             //println("class ${ui!!::class}")




             setContentView(
               ComposeView(this@MonoActivity).apply {
                 setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                 setContent {
                   Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                     when (ui) {
                       is AndroidViewUi -> AndroidView(
                           modifier = Modifier.fillMaxSize(),
                           factory = { context -> ui.view(context) }
                       )
                       is AndroidComposeUi -> ui.composition()
                       null -> { }
                       else -> throw IllegalStateException(
                         "UI must be an AndroidViewUi or an AndroidComposeUi. Instead found $this.")
                     }
                   }
                 }
               }
             )
           }
         }
         .collect()
       }
 }


 override fun onStop() {
   super.onStop()
   updateUiJob?.cancel()
 }


 companion object {
   /** Intent tag to record that the activity was launched by an intent from an AndroidRoot. */
   const val LAUNCHED_BY_ROOT = "launched_by_root"
 }
}





