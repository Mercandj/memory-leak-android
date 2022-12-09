package com.medium

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val listener = Manager.Listener {
        Log.d("memory_leak", "Use largeObject to avoid compiler opti $largeObject")
    }
    private var largeObject: Any? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        largeObject = (0..4_000).map {
            Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888)
        }
        manager.listeners.add(listener) // Leak without onDestroy() remove
        Log.d("memory_leak", "size: " + manager.listeners.size)
    }

    companion object {

        @JvmStatic
        private val manager = Manager()

        private class Manager {

            val listeners = HashSet<Listener>()

            @Suppress("unused")
            fun interface Listener {

                fun onChanged()
            }
        }
    }
}