package com.capotter.urbandictionary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(val word: String, val order: String) : ViewModelProvider.Factory {

    /**
     * Takes care of creation and updates to MainViewModel
     * @param modelClass: parameters to pass into MainViewModel
     */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(word, order) as T
    }


}