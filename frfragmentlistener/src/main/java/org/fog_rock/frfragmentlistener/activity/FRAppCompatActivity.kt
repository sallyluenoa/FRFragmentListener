package org.fog_rock.frfragmentlistener.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import org.fog_rock.frextensions.androidx.log.logI
import org.fog_rock.frfragmentlistener.dialog.FRDialogFragment
import org.fog_rock.frfragmentlistener.fragment.FRFragmentListener

/**
 * A subclass of AppCompatActivity to keep fragment listeners in the holder.
 */
open class FRAppCompatActivity: AppCompatActivity() {

    internal val fragmentListenerHolder: MutableMap<String, FRFragmentListener> = mutableMapOf()

    /**
     * Register a dialog callback in the holder to receive the result from the dialog.
     * @param callback A dialog callback
     * @return A key associated with the callback
     * @throws IllegalStateException If it is called after Activity#onCreate().
     * @sample org.fog_rock.frfragmentlistenersample.sample.SampleActivity.dialogCallbackKey
     */
    fun registerForDialogResult(callback: FRDialogFragment.Callback): String =
        registerForFragmentListener(callback)

    /**
     * Register a fragment listener in the holder to receive events from the fragment.
     * The registered listener would be restored in the fragment.
     * @param listener A fragment listener
     * @return A key associated with the listener
     * @throws IllegalStateException If it is called after Activity#onCreate().
     * @see org.fog_rock.frfragmentlistener.fragment.restoreFragmentEventListener
     * @sample org.fog_rock.frfragmentlistenersample.sample.SampleFragment.Listener
     * @sample org.fog_rock.frfragmentlistenersample.sample.SampleActivity.fragmentListenerKey
     */
    fun registerForFragmentListener(listener: FRFragmentListener): String {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {
            throw IllegalStateException(
                "Cannot register fragment listener. It must be called before CREATED stage.")
        }
        val key = "fragment_listener#${fragmentListenerHolder.size}"
        fragmentListenerHolder[key] = listener
        logI("Registered fragment listener. key: $key")
        return key
    }
}