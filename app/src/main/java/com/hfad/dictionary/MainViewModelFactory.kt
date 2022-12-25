package com.hfad.dictionary

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hfad.dictionary.repository.Repository

class ViewModelFactory(private val repository: Repository, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(com.hfad.dictionary.ViewModel.ViewModel::class.java)) {
            return com.hfad.dictionary.ViewModel.ViewModel(repository, application) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}