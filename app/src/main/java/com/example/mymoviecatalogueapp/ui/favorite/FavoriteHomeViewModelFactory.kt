package com.example.mymoviecatalogueapp.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymoviecatalogueapp.repository.MyRepository

class FavoriteHomeViewModelFactory(
    private val repository: MyRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavoriteHomeViewModel(repository) as T
    }

}