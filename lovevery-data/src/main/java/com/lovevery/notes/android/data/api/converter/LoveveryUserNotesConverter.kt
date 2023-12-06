package com.lovevery.notes.android.data.api.converter

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.lovevery.notes.android.data.api.model.NoteModelResponse
import com.lovevery.notes.android.data.api.model.NotesResponse
import com.lovevery.notes.android.data.api.model.UserNotesResponse
import okhttp3.ResponseBody
import retrofit2.Converter

class LoveveryUserNotesConverter(
    private val gson: Gson
) : Converter<ResponseBody, UserNotesResponse> {

    override fun convert(value: ResponseBody): UserNotesResponse {
        val jsonString = value.string()
        val jsonElement: JsonElement = gson.fromJson(jsonString, JsonElement::class.java)
        val statusCode = jsonElement.asJsonObject.get("statusCode").asInt
        val bodyString = jsonElement.asJsonObject.get("body").asString
        return when {
            statusCode != 200 -> {
                throw Exception("Server responded with an exception")
            }

            isEmptyJson(bodyString) -> {
                UserNotesResponse(
                    user = "",
                    notes = emptyList()
                )
            }

            else -> {
                val typeToken = object : TypeToken<UserNotesResponse>() {}.type
                gson.fromJson(bodyString, typeToken)
            }
        }
    }
}
