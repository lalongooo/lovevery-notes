package com.lovevery.notes.android.data.api.converter

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.lovevery.notes.android.data.api.model.NoteResponse
import okhttp3.ResponseBody
import retrofit2.Converter

class LoveveryPostNoteConverter(
    private val gson: Gson,
) : Converter<ResponseBody, NoteResponse> {
    override fun convert(value: ResponseBody): NoteResponse {
        val jsonString = value.string()
        val jsonElement: JsonElement = gson.fromJson(jsonString, JsonElement::class.java)
        val statusCode = jsonElement.asJsonObject.get("statusCode").asInt
        val bodyString = jsonElement.asJsonObject.get("body").asString
        return when {
            statusCode != 200 -> {
                throw Exception("Server responded with an exception")
            }

            else -> {
                val typeToken = object : TypeToken<NoteResponse>() {}.type
                gson.fromJson(bodyString, typeToken)
            }
        }
    }
}
