package com.example.stopwatch

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.stopwatch.databinding.ActivityMainBinding


private lateinit var binding: ActivityMainBinding
private lateinit var viewModel: MainActivityViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        binding = ActivityMainBinding.inflate(this.layoutInflater)
        setContentView(binding.root)

        setupUi()
        setupObservers()
    }

    private fun setupUi() {
        binding.startstopbtn.setOnClickListener {
            viewModel.startStop()
        }
        binding.lapresetbtn.setOnClickListener {
            viewModel.lapReset()
        }
    }

    private fun setupObservers() {
        viewModel.stopwatchTime.observe(this, Observer {
            binding.stopwatch.text = it
        })

        viewModel.runState.observe(this, Observer {
            updateButtonState(it)
        })

    }


    private fun updateButtonState(state:StopwatchState) {
        when (state) {
            StopwatchState.RUNNING -> {
                binding.startstopbtn.text = "Stop"
                binding.lapresetbtn.text = "Lap"
                binding.lapresetbtn.isEnabled = true
            }
            StopwatchState.INITIAL -> {
                binding.startstopbtn.text = "Start"
                binding.lapresetbtn.text = "Lap"
                binding.lapresetbtn.isEnabled = false
            }
            StopwatchState.PAUSED -> {
                binding.startstopbtn.text = "Start"
                binding.lapresetbtn.text = "Reset"
                binding.lapresetbtn.isEnabled = true
            }
        }
    }
}