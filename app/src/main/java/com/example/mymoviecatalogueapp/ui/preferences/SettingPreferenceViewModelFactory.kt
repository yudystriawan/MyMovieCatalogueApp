package com.example.mymoviecatalogueapp.ui.preferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymoviecatalogueapp.repository.MyRepository

class SettingPreferenceViewModelFactory(
    private val repository: MyRepository,
    private val date: String
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SettingPreferenceViewModel(repository, date) as T
    }
}