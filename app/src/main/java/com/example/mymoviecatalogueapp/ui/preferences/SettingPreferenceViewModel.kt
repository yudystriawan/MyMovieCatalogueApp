package com.example.mymoviecatalogueapp.ui.preferences

import androidx.lifecycle.ViewModel
import com.example.mymoviecatalogueapp.internal.lazyDeferred
import com.example.mymoviecatalogueapp.repository.MyRepository

class SettingPreferenceViewModel(
    private val repository: MyRepository,
    private val date: String
) : ViewModel() {

    val movies by lazyDeferred {
        repository.getDailyMovie(date, date)
    }
}