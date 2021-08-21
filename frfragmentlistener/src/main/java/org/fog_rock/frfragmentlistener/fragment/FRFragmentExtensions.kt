package org.fog_rock.frfragmentlistener.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import org.fog_rock.frextensions.androidx.log.logW
import org.fog_rock.frfragmentlistener.activity.FRAppCompatActivity

/**
 * Restore a fragment listener associated with a key from the activity holder.
 * @param args Arguments of a fragment
 * @param key A key associated with the listener
 * @return A fragment listener with the specified type
 * @see org.fog_rock.frfragmentlistener.activity.FRAppCompatActivity.registerForFragmentListener
 */
inline fun <reified T: FRFragmentListener> Fragment.restoreFragmentEventListener(args: Bundle, key: String): T? {
    val listener = restoreFRFragmentEventListener(args, key) ?: return null
    return listener as? T ?: run {
        logW("Invalid subclass of FragmentEventListener.")
        return null
    }
}

/**
 * Restore a fragment listener associated with a key from the activity holder.
 * It would be called by Fragment#restoreFragmentEventListener.
 * @param args Arguments of a fragment
 * @param key A key associated with the listener
 * @return A fragment listener
 * @see org.fog_rock.frfragmentlistener.fragment.restoreFragmentEventListener
 */
fun Fragment.restoreFRFragmentEventListener(args: Bundle, key: String): FRFragmentListener? {
    val callbackKey = args.getString(key) ?: run {
        logW("Not found fragment listener key.")
        return null
    }
    val activity = requireActivity() as? FRAppCompatActivity ?: run {
        logW("Activity does not extends FRAppCompatActivity.")
        return null
    }
    return activity.fragmentListenerHolder[callbackKey] ?: run {
        logW("Not found fragment listener from holders.")
        return null
    }
}