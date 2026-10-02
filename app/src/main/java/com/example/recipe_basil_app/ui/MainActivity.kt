package com.example.recipe_basil_app.ui

import android.os.Build
import android.os.Bundle
import android.view.Window
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsCompat.Type
import androidx.core.view.updatePadding
import com.example.recipe_basil_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle system bar insets for navigation bars
//        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
//            val systemBarsInsets = insets.getInsets(Type.systemBars())
//            view.updatePadding(bottom = systemBarsInsets.bottom)
//            WindowInsetsCompat.CONSUMED
//        }
    }
}