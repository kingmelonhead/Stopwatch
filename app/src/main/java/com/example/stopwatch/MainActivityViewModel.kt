package com.example.stopwatch

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.concurrent.TimeUnit


class MainActivityViewModel : ViewModel() {

    var stopwatchTime: MutableLiveData<String> = MutableLiveData()
        private set

    var runState: MutableLiveData<StopwatchState> = MutableLiveData()
        private set

    var lapNum: MutableLiveData<Int> = MutableLiveData()
        private set

    var lapTime: MutableLiveData<String> = MutableLiveData()
        private set

    var lapList: MutableLiveData<ArrayList<ExampleOutput>> = MutableLiveData()
        private set

    private val handler: Handler = Handler(Looper.getMainLooper())
    private var timeStamp: Long = 0
    private var lastTimeStamp: Long = 0
    private var totalTimeStamp: Long = 0
    private var tempTime : Long = 0
    private var lastLap : Long = 0

    init {
        runState.value = StopwatchState.INITIAL
        lapNum.value = 0
        lapList.value = arrayListOf()
    }

    fun startStop() {
        when (runState.value) {
            StopwatchState.INITIAL -> { // If btn is pressed while stopwatch is in initial state
                runState.value = StopwatchState.RUNNING // Start the stopwatch
                timeStamp = System.currentTimeMillis()
            }
            StopwatchState.RUNNING -> { // If btn is pressed while stopwatch is in running state
                runState.value = StopwatchState.PAUSED // Pause the stopwatch
            }
            StopwatchState.PAUSED -> { // If btn is pressed while stopwatch is in running state
                runState.value = StopwatchState.RUNNING // Start the stopwatch
                timeStamp = System.currentTimeMillis()
                lastTimeStamp = totalTimeStamp
            }
        }

        tick()
    }

    fun lapReset() {
        when (runState.value) {
            StopwatchState.PAUSED -> {
                runState.value = StopwatchState.INITIAL
                stopwatchTime.value = "00:00.000"
                totalTimeStamp = 0
                lapNum.value = 0
                lastLap = 0
                lapList.value?.clear()
                lapList.value= arrayListOf()

            }
            StopwatchState.RUNNING -> {
                // Add a Lap
                lapNum.value = lapNum.value?.plus(1)
                recordLap()
            }

            else -> {} // Everything else do nothing
        }
    }

    private fun tick(speed: Long = 10) {
        if (runState.value == StopwatchState.RUNNING) {
            handler.postDelayed(Runnable {
                totalTimeStamp = lastTimeStamp + (System.currentTimeMillis() - timeStamp)
                updateTime()
                tick(speed)
            }, speed)
        }
    }

    private fun recordLap() {
        var tempList : ArrayList<ExampleOutput> = arrayListOf<ExampleOutput>()
        tempList = lapList.value!!
        tempTime = totalTimeStamp - lastLap
        lastLap = totalTimeStamp
        Log.e("Tag", "Recording lap")
        val centiSeconds = String.format("%03d", tempTime % 1000)
        val seconds = String.format("%02d", TimeUnit.MILLISECONDS.toSeconds(tempTime))
        val minutes = String.format("%02d", TimeUnit.MILLISECONDS.toMinutes(tempTime))
        lapTime.value = "$minutes:$seconds.$centiSeconds"
        Log.e(null, "Attempting to add to list")
        Log.e(null, "lap number: ${lapNum.value}")
        Log.e(null, "lap time trying to be used to make new item: ${lapTime.value}")
        Log.e(null, "size of list at this time (before attempting to make new item): ${lapList.value?.size} $")
        tempList.add(ExampleOutput(lapTime.value!!, lapNum.value!!))
        lapList.value = tempList
        Log.e(null, "size of list at this time (after attempting to make new item): ${lapList.value?.size} $")
        Log.e(null, "lap time from list: ${lapList.value?.get(lapNum.value!! - 1)?.lap_time}")
    }

    private fun updateTime() {
        val centiSeconds = String.format("%03d", totalTimeStamp % 1000)
        val seconds = String.format("%02d", TimeUnit.MILLISECONDS.toSeconds(totalTimeStamp))
        val minutes = String.format("%02d", TimeUnit.MILLISECONDS.toMinutes(totalTimeStamp))
        stopwatchTime.value = "$minutes:$seconds.$centiSeconds"
    }
}