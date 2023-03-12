package com.alex.gardenia.utils

import com.elvishew.xlog.XLog
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException

object HttpRequestUtil {

    suspend fun httpGet(
        requestAddress: String,
        authorization: String?,
        onFailure: () -> Unit,
        onSuccess: (Result<Any>) -> Unit
    ) {
        val builder = Request.Builder().get().url(requestAddress)
        authorization?.let { builder.addHeader("Authorization", authorization) }
        val request = builder.build()
        val call = OkHttpClient().newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                XLog.e("Http Get Fail", e)
                onFailure()
            }

            override fun onResponse(call: Call, response: Response) {
                val result =
                    Gson().fromJson(response.body.string(), object : TypeToken<Result<Any>>() {})
                onSuccess(result)
            }
        })
    }

    suspend fun httpGet(
        requestAddress: String,
        authorization: String?,
        params: Map<String, String>,
        onFailure: () -> Unit,
        onSuccess: (Result<Any>) -> Unit
    ) {
        val urlWithParams = StringBuilder(requestAddress).append("?")
        params.forEach { (k, v) ->
            urlWithParams.append(k).append("=").append(v).append("&")
        }
        urlWithParams.deleteCharAt(urlWithParams.lastIndex)
        val builder = Request.Builder().get()
        authorization?.let { builder.addHeader("Authorization", authorization) }
        val request = builder.build()
        val call = OkHttpClient().newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                XLog.e("Http Get with params Fail", e)
                onFailure()
            }

            override fun onResponse(call: Call, response: Response) {
                val result =
                    Gson().fromJson(response.body.string(), object : TypeToken<Result<Any>>() {})
                onSuccess(result)
            }
        })
    }

    suspend fun httpPost(
        requestAddress: String,
        authorization: String?,
        params: Map<String, String>,
        onFailure: () -> Unit,
        onSuccess: (Result<Any>) -> Unit
    ) {
        val form = FormBody.Builder()
        params.forEach { (k, v) -> form.add(k, v) }
        val builder = Request.Builder().post(form.build()).url(requestAddress)
        authorization?.let { builder.addHeader("Authorization", authorization) }
        val request = builder.build()
        val call = OkHttpClient().newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                XLog.e("Http Get Fail", e)
                onFailure()
            }

            override fun onResponse(call: Call, response: Response) {
                val result =
                    Gson().fromJson(response.body.string(), object : TypeToken<Result<Any>>() {})
                onSuccess(result)
            }
        })
    }

    suspend fun httpPost(
        requestAddress: String,
        json: JsonElement,
        authorization: String?,
        onFailure: () -> Unit,
        onSuccess: (Result<Any>) -> Unit
    ) {
        val requestBody =
            Gson().toJson(json).toRequestBody("application/json;charset=utf-8".toMediaType())
        val builder = Request.Builder().post(requestBody).url(requestAddress)
        authorization?.let { builder.addHeader("Authorization", authorization) }
        val request = builder.build()
        val call = OkHttpClient().newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                XLog.e("Http Get Fail", e)
                onFailure()
            }

            override fun onResponse(call: Call, response: Response) {
                val result =
                    Gson().fromJson(response.body.string(), object : TypeToken<Result<Any>>() {})
                onSuccess(result)
            }
        })
    }

}