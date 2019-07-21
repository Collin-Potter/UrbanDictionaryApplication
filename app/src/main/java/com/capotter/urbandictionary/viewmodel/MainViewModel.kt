package com.capotter.urbandictionary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capotter.urbandictionary.model.CurrentState
import com.capotter.urbandictionary.model.Definition

class MainViewModel(var word: String, var order: String) : ViewModel() {

    val definitionRepository = DefinitionRepository()
    val allDefinitions: LiveData<List<Definition>> get() = definitionRepository.getMutableLiveData(word, order)
    val currentState: LiveData<CurrentState> get() = definitionRepository.getLoading()

    /**
     * Update MainViewModel term and/or order to alter getMutableLiveData returns
     * @param term: Desired term to be defined
     * @param orderBy: Desired order to stylize view
     */
    fun updateTerm(term: String, orderBy: String){
        word = term
        order = orderBy
    }

    /**
     * Cancel job if issue arises or app is closed
     */
    override fun onCleared() {
        super.onCleared()
        definitionRepository.completableJob.cancel()
    }
}