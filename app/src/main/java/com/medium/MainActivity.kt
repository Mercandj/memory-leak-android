package com.medium

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val searchListener = SearchManager.Listener {
        Log.d("memory_leak", "use largeObject to avoid compiler opti $largeObject")
    }
    private val largeObject = (0..2_000).map {
        Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchManager.listeners.add(searchListener) // Leak without listeners.removeListener on onDestroy()
        Log.d("jm/debug", "size: " + searchManager.listeners.size)
    }

    companion object {

        @JvmStatic
        private val searchManager = SearchManager()

        private class SearchManager {

            val listeners = HashSet<Listener>()

            fun interface Listener {
                fun onChanged()
            }
        }
    }
}