package com.davidvelez.mercadolibrecostsimulator.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.davidvelez.mercadolibrecostsimulator.databinding.ActivitySplashBinding
import com.davidvelez.mercadolibrecostsimulator.ui.costcalculator.CostCalculator
import java.util.*
import kotlin.concurrent.timerTask

class SplashActivity : AppCompatActivity() {
    private lateinit var splashBinding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashBinding = ActivitySplashBinding.inflate(layoutInflater)
        val view = splashBinding.root
        setContentView(view)

        val timer = Timer()
        timer.schedule(timerTask {
            val intent = Intent(this@SplashActivity,CostCalculator::class.java)
            startActivity(intent)
            finish()
        }, 2000)

    }
}