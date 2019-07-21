package com.capotter.urbandictionary.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Object in which list of Definition objects will be gathered from due to JSON format of API call response
 */
@JsonClass(generateAdapter = true)
data class DefinitionWrapper (
    @Json(name="list")
    var list: MutableList<Definition>? = null
)