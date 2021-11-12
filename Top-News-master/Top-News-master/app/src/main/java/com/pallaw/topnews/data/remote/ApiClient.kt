package com.pallaw.topnews.data.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Created by Pallaw Pathak on 10/05/20. - https://www.linkedin.com/in/pallaw-pathak-a6a324a1/
 */

object ApiClient {

    const val API_KEY = "e030bf7052314b9db62d89c0565dfe09"
    const val BASE_URL = "https://newsapi.org/v2/"

    const val FIRST_PAGE = 1
    const val ITEM_PER_PAGE = 15
    const val COUNTRY = "in"

    fun getClient(): Api {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(getCommonRequestInterceptor())
            .addInterceptor(getLoggingInterceptor())
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)

    }

    private fun getCommonRequestInterceptor(): Interceptor {
        return object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val url = chain.request()
                    .url
                    .newBuilder()
                    .addQueryParameter("apiKey", API_KEY)
                    .addQueryParameter("country", COUNTRY)
                    .addQueryParameter("pageSize", "$ITEM_PER_PAGE")
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return chain.proceed(request)
            }
        }
    }

    private fun getLoggingInterceptor(): Interceptor {
        return object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request: Request = chain.request()
                Timber.tag("Okhttp_request").d(request.toString())

                val response = chain.proceed(request)
                val rawJson = response.body!!.string()

                Timber.tag("Okhttp_response").d(rawJson)

                return response.newBuilder()
                    .body(rawJson.toResponseBody(response.body!!.contentType())).build()
            }
        }
    }
}