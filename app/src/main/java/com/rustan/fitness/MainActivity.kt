package com.rustan.fitness

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager


import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import com.rustan.fitness.databinding.ActivityMainBinding
import java.util.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private var cup = 0
    var steps = 0
    var steps2 = 0
    var lox = "lox"
    var kall = "kall"

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        reset_fun()
        steps_fun()
        water_click_listener()

    }

    private fun steps_fun() {
        var sManager : SensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        val sListener = object : SensorEventListener{
            override fun onSensorChanged(event: SensorEvent?) {
                steps = event!!.values[0].toInt()
                steps2 = steps - 209
                lox = (steps2*0.001).toString()
                kall =(steps2*0.02).toString()
                binding.stepText.text = steps2.toString()
                binding.km.text = "$lox km"
                binding.kcal.text = "$kall kcal"
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

            }
        }
        sManager.registerListener(sListener,sensor,SensorManager.SENSOR_DELAY_FASTEST)
    }


    private fun reset_fun() {
        thread {
            while (true) {
                var timeH = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                var timeM = Calendar.getInstance().get(Calendar.MINUTE)
                var timeS = Calendar.getInstance().get(Calendar.SECOND)
                if (timeH == 0 && timeM == 0 && timeS == 0) {
                    runOnUiThread {
                        cup = 0
                        binding.cupDrinkText.text = cup.toString()
                        steps = 0
                        steps2 = 0
                        lox="0"
                        kall="0"
                        binding.stepText.text = steps2.toString()
                        binding.km.text = "$lox km"
                        binding.kcal.text = "$kall kcal"
                    }

                }
            }
        }
    }


    private fun water_click_listener() = with(binding) {
        cupDrinkHolder.setOnClickListener {
            ++cup
            cupDrinkText.text = cup.toString()
        }
    }



}
