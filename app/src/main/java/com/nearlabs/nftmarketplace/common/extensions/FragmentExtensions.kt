package com.nearlabs.nftmarketplace.common.extensions

import android.app.Application
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController


@Suppress("UNCHECKED_CAST")
fun <F : Fragment> AppCompatActivity.getFragment(fragmentClass: Class<F>): F? {
    val navHostFragment = this.supportFragmentManager.fragments.first() as NavHostFragment

    navHostFragment.childFragmentManager.fragments.forEach {
        if (fragmentClass.isAssignableFrom(it.javaClass)) {
            return it as F
        }
    }

    return null
}

fun provideFactory(application: Application, bundle: Bundle) =
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return try {
                modelClass.getConstructor(Application::class.java, Bundle::class.java)
                    .newInstance(application, bundle)
            } catch (e: Exception) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            }
        }
    }

fun provideFactory(application: Application) =
    object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return try {
                modelClass.getConstructor(Application::class.java)
                    .newInstance(application)
            } catch (e: Exception) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            }
        }
    }

fun <T> Fragment.getNavigationResult(
    key: String = "result",
    destinationId: Int? = null
): LiveData<T>? {
    val backStack = destinationId?.let { findNavController().getBackStackEntry(it) }
        ?: findNavController().currentBackStackEntry

    return backStack?.savedStateHandle?.getLiveData(key)
}

fun <T> Fragment.setNavigationResult(result: T, key: String = "result") {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}

fun <T> Fragment.setCurrentNavigationResult(key: String = "result", result: T?) {
    findNavController().currentBackStackEntry?.savedStateHandle?.set(key, result)
}

fun <T> Fragment.setPreviousNavigationResult(key: String = "result", result: T?) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}

fun <T> Fragment.getCurrentNavigationResult(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)

fun <T> Fragment.getPreviousNavigationResult(key: String = "result") =
    findNavController().previousBackStackEntry?.savedStateHandle?.getLiveData<T>(key)

fun <T> Fragment.removeCurrentNavigationResult(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.remove<T>(key)

fun <T> Fragment.removePreviousNavigationResult(key: String = "result") =
    findNavController().previousBackStackEntry?.savedStateHandle?.remove<T>(key)


fun <T> Fragment.removeNavigationResult(key: String = "result", destinationId: Int? = null) {
    val backStack = destinationId?.let { findNavController().getBackStackEntry(it) }
        ?: findNavController().currentBackStackEntry
    backStack?.savedStateHandle?.remove<T>(key)
}

fun Fragment.popBack(): Boolean {
    return NavHostFragment.findNavController(this).navigateUp()
}

fun Fragment.popBackTo(destinationId: Int, inclusive:Boolean = false): Boolean {
    return NavHostFragment.findNavController(this).popBackStack(destinationId, inclusive)
}

fun FragmentActivity.onBackPressedOverride(func: () -> Unit?, viewLifecycleOwner: LifecycleOwner) {
    this.onBackPressedDispatcher.addCallback(
        viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                func()
            }
        })
}

fun Fragment.onBackPressedOverride(func: () -> Unit?) {
    activity?.onBackPressedOverride(func, viewLifecycleOwner)
}

fun Fragment.observeOnDestroy(action: () -> Unit) {
    viewLifecycleOwnerLiveData.observe(viewLifecycleOwner) { viewLifecycleOwner ->
        viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                action.invoke()
            }
        })
    }
}