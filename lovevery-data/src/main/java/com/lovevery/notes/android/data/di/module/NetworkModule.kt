package com.lovevery.notes.android.data.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.lovevery.notes.android.data.api.ApiService
import com.lovevery.notes.android.data.api.converter.LoveveryBodyConverterFactory
import com.lovevery.notes.android.data.api.model.OperationType
import com.lovevery.notes.android.data.api.serializer.OperationTypeSerializer
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Modifier
import javax.inject.Singleton

@Module
class NetworkModule(private val baseUrl: String) {

    @Provides
    @Singleton
    fun provideHttpClient(
        httpLoggingInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        gson: Gson,
        httpClient: OkHttpClient,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(LoveveryBodyConverterFactory.create(gson))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .build()

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .excludeFieldsWithModifiers(Modifier.TRANSIENT)
        .registerTypeAdapter(OperationType::class.java, OperationTypeSerializer())
        .disableHtmlEscaping().create()

    @Provides
    @Singleton
    fun provideApiService(
        retrofit: Retrofit
    ): ApiService = retrofit.create(
        ApiService::class.java
    )

    @Provides
    @Singleton
    fun provideRxJavaCallAdapterFactory(): RxJava2CallAdapterFactory =
        RxJava2CallAdapterFactory.create();

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): Interceptor = HttpLoggingInterceptor().setLevel(BODY)
}
