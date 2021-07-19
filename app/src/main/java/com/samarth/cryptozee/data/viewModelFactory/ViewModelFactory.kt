package com.samarth.cryptozee.data.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.samarth.cryptozee.data.repository.Repository
import com.samarth.cryptozee.data.viewmodel.MainViewModel

class ViewModelFactorys(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}