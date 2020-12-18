package com.example.architecturesandbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create instance of our VM class
        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)


        // Call observer to the LiveData function created in the VM class
        viewModel.getSeconds().observe(this, {
            textview_number.text = it.toString()
        })

        // Observer to finished
        viewModel.finished.observe(this, {
            if (it){
                viewModel.makeToast(this, "Countdown finished")
            }
        })

        // Start button on-click listener
        start_button.setOnClickListener{
            if (editText.text.isEmpty() || editText.text.length < 4){
                viewModel.makeToast(this, "Invalid Number")
            } else {
                viewModel.timerValue.value = editText.text.toString().toLong()
                // Start timer
                viewModel.startTimer()
            }
        }

        // Pause button listener
        pause_button.setOnClickListener{
            if (viewModel.getSeconds().value != null){
                if (!viewModel.paused){
                    viewModel.pauseTimer()
                    pause_button.text = "Resume"
                    viewModel.paused = true
                } else {
                    viewModel.resumeTimer()
                    pause_button.text = "Pause"
                    viewModel.paused = false
                }
            }
        }

        // Stop button on-click listener
        stop_button.setOnClickListener{
            viewModel.stopTimer()
        }
    }
}