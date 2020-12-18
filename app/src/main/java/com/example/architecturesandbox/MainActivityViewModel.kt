package com.example.architecturesandbox

import android.content.Context
import android.os.CountDownTimer
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {
    var pausePoint = 0
    var paused = false

    private lateinit var countDownTimer: CountDownTimer
    private val _seconds = MutableLiveData<Int>()

    var finished = MutableLiveData<Boolean>()

    var timerValue = MutableLiveData<Long>()

    fun getSeconds(): LiveData<Int> {
        return _seconds
    }

    fun startTimer(){
        countDownTimer = object : CountDownTimer(timerValue.value!!.toLong(), 1000){
            override fun onTick(p0: Long) {
                val timeLeft = p0/1000
                _seconds.value = timeLeft.toInt()
            }
            override fun onFinish() {
                finished.value = true
            }
        }.start()
    }

    fun pauseTimer(){
        if (_seconds.value != null) {
            pausePoint = _seconds.value!! * 1000
            countDownTimer.cancel()
        }
    }

    fun resumeTimer(){
        timerValue.value = pausePoint.toLong()
        startTimer()
    }

    fun stopTimer(){
        if (_seconds.value != null){
            countDownTimer.cancel()
            _seconds.value = 0
        }
    }

    fun makeToast(context: Context, message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}