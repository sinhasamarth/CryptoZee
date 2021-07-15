package com.samarth.cryptozee.ui.base.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.samarth.cryptozee.data.repository.Repository
import com.samarth.cryptozee.ui.base.viewmodel.MainViewModel

class MainViewModelFactory(private val repository: Repository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}