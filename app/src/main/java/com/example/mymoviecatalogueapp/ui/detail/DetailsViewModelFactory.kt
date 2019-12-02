package com.example.mymoviecatalogueapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymoviecatalogueapp.repository.MyRepository

class DetailsViewModelFactory(
    private val repository: MyRepository,
    private val id: Int
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailsViewModel(repository, id) as T
    }
}