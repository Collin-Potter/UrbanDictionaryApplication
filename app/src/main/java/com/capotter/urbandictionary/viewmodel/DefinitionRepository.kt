package com.capotter.urbandictionary.viewmodel

import androidx.lifecycle.MutableLiveData
import android.util.Log
import com.capotter.urbandictionary.model.CurrentState
import com.capotter.urbandictionary.model.Definition
import com.capotter.urbandictionary.networking.RestApiService
import kotlinx.coroutines.*
import retrofit2.HttpException

class DefinitionRepository {

    private var definitions = mutableListOf<Definition>()
    private var mutableLiveData = MutableLiveData<List<Definition>>()
    private var currentStateData = MutableLiveData<CurrentState>()
    val completableJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + completableJob)

    /**
     * Initialize RestApiService and start
     */
    private val thisApiCorService by lazy {
        RestApiService.createCorService()
    }

    /**
     * Launch coroutine to call API for list of desired definitions
     * @param word: desired term to define
     * @param order: desired order in which list is displayed
     */
    fun getMutableLiveData(word: String, order: String): MutableLiveData<List<Definition>> {
        setLoading(true)
        coroutineScope.launch {
            Log.d("TEST", "Getting data for '$word' in '$order' order")
            val request = thisApiCorService.getDefinition(word)
            withContext(Dispatchers.Main) {
                try {
                    val response = request.await()
                    val mDefinitionWrapper = response
                    if (mDefinitionWrapper != null && mDefinitionWrapper.list != null) {
                        definitions = mDefinitionWrapper.list as MutableList<Definition>
                        when(order){
                            "thumbs_up" -> definitions.sortByDescending { it.thumbs_up }
                            "thumbs_down" -> definitions.sortByDescending { it.thumbs_down }
                        }
                        mutableLiveData.value = definitions
                        setLoading(false)
                    }

                } catch (e: HttpException) {
                    e.printStackTrace()
                } catch (e: Throwable){
                    e.printStackTrace()
                }
            }
        }
        return mutableLiveData
    }

    /**
     * Set loading status of background task
     * @param state: desired state to set background task
     */
    private fun setLoading(state: Boolean){
        currentStateData.value = CurrentState(state)
        currentStateData.value!!.isLoading = state
    }

    /**
     * Return loading status of background task
     * @return state object for current state observations
     */
    fun getLoading(): MutableLiveData<CurrentState> {
        return currentStateData
    }

}