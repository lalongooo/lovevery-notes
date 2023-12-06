package com.lovevery.notes.android.data.api.converter

import com.google.gson.JsonParser

fun isEmptyJson(jsonString: String): Boolean {
    val jsonElement = JsonParser().parse(jsonString)
    if (jsonElement.isJsonObject) {
        val jsonObject = jsonElement.asJsonObject
        return jsonObject.entrySet().isEmpty()
    }
    return false
}