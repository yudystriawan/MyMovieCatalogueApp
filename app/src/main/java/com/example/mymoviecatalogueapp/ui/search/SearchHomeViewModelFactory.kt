package com.example.mymoviecatalogueapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymoviecatalogueapp.repository.MyRepository

class SearchHomeViewModelFactory(
    private val repository: MyRepository,
    private val query: String
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchHomeViewModel(repository, query) as T
    }
}