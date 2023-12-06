package com.lovevery.notes.android.data.di.module

import com.google.gson.Gson
import com.lovevery.notes.android.data.api.model.NotesResponse
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class LoveveryBodyConverterFactory private constructor(
    private val gson: Gson
) : Converter.Factory() {

    fun create(gson: Gson): LoveveryBodyConverterFactory {
        return LoveveryBodyConverterFactory(gson)
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        if (type == NotesResponse::class.java) {
            return LoveveryBodyConverter(this.gson)
        }
        return null
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, okhttp3.RequestBody>? {
        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit)
    }

    companion object {
        fun create(gson: Gson): LoveveryBodyConverterFactory {
            return LoveveryBodyConverterFactory(gson)
        }
    }
}
