package com.capotter.urbandictionary.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Object in which each JSONObject is formatted to gather all essential information from API call
 */
@JsonClass(generateAdapter = true)
data class Definition (
    @Json(name="word")
    var word: String? = null,
    @Json(name="definition")
    var definition: String? = null,
    @Json(name="thumbs_up")
    var thumbs_up: Int? = null,
    @Json(name="thumbs_down")
    var thumbs_down: Int? = null
)