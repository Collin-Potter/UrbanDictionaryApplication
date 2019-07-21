package com.capotter.urbandictionary.networking

import com.capotter.urbandictionary.model.DefinitionWrapper
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface RestApiService {

    /**
     * Assign essential headers and get term in the following format: /define?term={term}
     * @param term: term to be defined
     * @return job to be completed for JSON response
     */
    @Headers("X-RapidAPI-Key: de1e4b39d6mshebe31e8083f1bd3p151421jsn5e766d570bc0", "X-RapidAPI-Host: mashape-community-urban-dictionary.p.rapidapi.com")
    @GET("/define") //TODO: term={currentGlobalWordRequest}
    fun getDefinition(@Query("term") term: String): Deferred<DefinitionWrapper>

    companion object {

        /**
         * Build necessary information for successful API calls
         */
        fun createCorService(): RestApiService {


            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl("https://mashape-community-urban-dictionary.p.rapidapi.com")
                .addConverterFactory(MoshiConverterFactory.create())
                .client(okHttpClient)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build().create(RestApiService::class.java)
        }
    }
}