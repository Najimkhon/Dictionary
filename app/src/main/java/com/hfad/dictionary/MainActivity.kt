package com.hfad.dictionary

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.hfad.dictionary.viewmodel.MainViewModel
import com.hfad.dictionary.viewmodel.SharedViewModel

class MainActivity : AppCompatActivity() {
    private val sharedViewModel: SharedViewModel by viewModels()
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            this.application
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}