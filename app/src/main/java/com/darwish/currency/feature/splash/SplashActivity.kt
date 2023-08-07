package com.darwish.currency.feature.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.darwish.currency.R
import com.darwish.currency.core.extention.viewBinding
import com.darwish.currency.databinding.ActivitySplashBinding
import com.darwish.currency.feature.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivitySplashBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lifecycleScope.launch {
            delay(2.seconds)
            goToMain()
        }
    }


    private fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


}