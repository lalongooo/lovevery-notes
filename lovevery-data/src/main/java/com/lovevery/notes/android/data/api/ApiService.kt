package com.lovevery.notes.android.data.api

import com.lovevery.notes.android.data.api.model.NoteRequest
import com.lovevery.notes.android.data.api.model.NoteResponse
import com.lovevery.notes.android.data.api.model.NotesResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("proto/messages")
    fun addNote(@Body messageRequest: NoteRequest): Single<NoteResponse>

    @GET("proto/messages")
    fun getNotes(): Single<NotesResponse>
}