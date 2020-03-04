package com.jasvir.cookingrecord.services

import com.google.gson.GsonBuilder
import com.jasvir.cookingrecord.common.BASE_URL
import com.jasvir.cookingrecord.model.Response
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import kotlin.collections.HashMap

interface CookingRecordApi {
    @GET("cooking_records")
    fun allCharacters(@QueryMap map: HashMap<String,String>, @Query("offset") offset: Int? = 0): Observable<Response>

    companion object {
        fun getService(): CookingRecordApi {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)
            httpClient.addInterceptor { chain ->
                val original = chain.request()
                val originalHttpUrl = original.url()

                val url = originalHttpUrl.newBuilder()
                    .build()

                chain.proceed(original.newBuilder().url(url).build())
            }

            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build()

            return retrofit.create<CookingRecordApi>(CookingRecordApi::class.java)
        }
    }
}