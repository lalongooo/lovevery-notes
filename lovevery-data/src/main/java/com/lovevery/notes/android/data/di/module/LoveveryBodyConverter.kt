package com.lovevery.notes.android.data.di.module

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.lovevery.notes.android.data.api.model.NoteModelResponse
import com.lovevery.notes.android.data.api.model.NotesResponse
import okhttp3.ResponseBody
import retrofit2.Converter

class LoveveryBodyConverter(
    private val gson: Gson
) : Converter<ResponseBody, NotesResponse> {

    override fun convert(value: ResponseBody): NotesResponse {
        val jsonString = value.string()
        val jsonElement: JsonElement = gson.fromJson(jsonString, JsonElement::class.java)
        val statusCode = jsonElement.asJsonObject.get("statusCode").asInt
        val bodyString = jsonElement.asJsonObject.get("body").asString
        return if (isEmptyJson(bodyString)) {
            NotesResponse(
                statusCode = -1,
                notes = emptyMap()
            )
        } else {
            val typeToken = object : TypeToken<Map<String, List<NoteModelResponse>>>() {}.type
            val notes = gson.fromJson<Map<String, List<NoteModelResponse>>>(bodyString, typeToken)
            NotesResponse(
                statusCode = statusCode,
                notes = notes
            )
        }
    }

    private fun isEmptyJson(jsonString: String): Boolean {
        val jsonElement = JsonParser().parse(jsonString)
        if (jsonElement.isJsonObject) {
            val jsonObject = jsonElement.asJsonObject
            return jsonObject.entrySet().isEmpty()
        }
        return false
    }
}
